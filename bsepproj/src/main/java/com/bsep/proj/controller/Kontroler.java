package com.bsep.proj.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bsep.proj.model.Certificates;
import com.bsep.proj.model.CsrRequest;
import com.bsep.proj.ostalo.MessageWithObj;
import com.bsep.proj.ostalo.SubjectInfo;
import com.bsep.proj.repository.CertServiceImpl;

import bezbednostKlase.CertificateGenerator;
import bezbednostKlase.IssuerData;
import bezbednostKlase.KeyStoreReader;
import bezbednostKlase.KeyStoreWriter;
import bezbednostKlase.SubjectData;


@RestController
@RequestMapping("/")
public class Kontroler {
	
	  private final String COUNTRY = "2.5.4.6";
	    private final String STATE = "2.5.4.8";
	    private final String LOCALE = "2.5.4.7";
	    private final String ORGANIZATION = "2.5.4.10";
	    private final String ORGANIZATION_UNIT = "2.5.4.11";
	    private final String COMMON_NAME = "2.5.4.3";
	
	@Autowired
	CertServiceImpl service;
	@RequestMapping(value = "/serialinfo/{serial}" , produces = "application/pkix-cert")
	public void certInfo(@PathVariable("serial") String serial,
			HttpServletResponse response, HttpServletRequest request) throws IOException{
		long ser = Long.parseLong(serial);
		System.out.println("LONG ser: "+ser+"     BIGint ser: "+BigInteger.valueOf(ser));
		KeyStoreWriter ksw = new KeyStoreWriter();
		KeyStoreReader ksr = new KeyStoreReader();
		X509Certificate xcert=null;
		ksw.loadKeyStore("./data/keystore.jks", "admin".toCharArray());
		
		Enumeration<String> aliases = ksw.getAlias();
		ArrayList<String> certList = new ArrayList<String>();
		while (aliases.hasMoreElements()) {
			String show = aliases.nextElement();
			certList.add(show);
		}
		
		System.out.println("ISPISS ROOT ALIAS:  "+certList.get(0));
		java.security.cert.Certificate cert=null;
		String alias = "";
		
		for(int i=0; i<certList.size(); i++){//TODO cuvati na nekom mestu valjda
			cert = ksr.readCertificate("./data/keystore.jks", "admin", certList.get(i));
			 xcert = (X509Certificate)cert;
			 
			if(xcert.getSerialNumber().compareTo(BigInteger.valueOf(ser))==0){
				alias = certList.get(i);
				System.out.println("USAO!!");
				break;
			}
		}
	
		System.out.println("ALIAS POSLE FORA: "+certList.get(0));
		
		ksw.save(xcert, "./exported/"+alias);
		File file = new File("./exported/"+alias+".cer");
		
		
		Path path = Paths.get("./exported/"+alias+".cer");
		byte[] data = Files.readAllBytes(path);
		
		 
         Path putanja = Paths.get("C:\\Users\\me\\Desktop\\" +alias+".cer");
         Files.write(putanja, data);
	}
	
	 private File getFile(String alias) throws FileNotFoundException {
		 
	        File file = new File("./exported/"+alias+".cer");
	        if (!file.exists()){
	            throw new FileNotFoundException("file with path: " + "/exported/"+alias+".cer" + " was not found.");
	        }
	        return file;
	    }
	@RequestMapping(value = "/povuciserial/{serial}",produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageWithObj  povuciSerial(@PathVariable("serial") String serial){
		long ser = Long.parseLong(serial);
		
		Certificates cer = service.vratiPrekoSerijskog(ser);
		if(!cer.isOcspValid())return new MessageWithObj("Vec je povucen", true, null);
		else{
			service.povuciSertifikat(ser);
			return new MessageWithObj("Uspesno povucen", true, null);
		}
	
	
	}
	@RequestMapping(value = "/ocspstatus/{serial}",produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageWithObj  ocspStatus(@PathVariable("serial") String serial){
		long ser = Long.parseLong(serial);
		
		
		Certificates cer = service.vratiPrekoSerijskog(ser);
		if(!cer.isOcspValid())return new MessageWithObj("Sertifikat nije u upotrebi!", true, null);
		else{
			
			
			return new MessageWithObj("Sertifikat je validan!", true, null);
			
			
		}
	
	
	}
	
	
	
	@RequestMapping(value = "/selfsign",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public  MessageWithObj selfSignCert(@RequestBody SubjectInfo info) throws ParseException, InvalidKeyException, CertificateException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException{
		
		System.out.println(info.getEmail());
		KeyPair keyPairIssuer=null;
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			keyPairIssuer=keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		
		//Datumi od kad do kad vazi sertifikat
		SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
		//Date startDate = iso8601Formater.parse("2016-12-31");
		//Date endDate = iso8601Formater.parse("2022-12-31");
		Date startDate = info.getStartDate();
		Date endDate = info.getEndDate();
		//Serijski broj sertifikata
		String sn= Long.toString(System.currentTimeMillis());
		//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		  builder.addRDN(BCStyle.CN, info.getIme());
		    builder.addRDN(BCStyle.SURNAME, info.getPrezime());
		    builder.addRDN(BCStyle.GIVENNAME, info.getIme()+" "+info.getPrezime());
		    builder.addRDN(BCStyle.O, info.getKompanija());
		    builder.addRDN(BCStyle.OU, info.getOrgUnit());
		    builder.addRDN(BCStyle.C, "RS");
		    builder.addRDN(BCStyle.E, info.getEmail());
		    //UID (USER ID) je ID korisnika
		    String uid =  Long.toString(System.currentTimeMillis());
		    builder.addRDN(BCStyle.UID, uid);
	    
	
	   
		IssuerData issuerData = generateSelfCertIssuerData(keyPairIssuer.getPrivate(), info,  uid);
		SubjectData subjectData= new SubjectData(keyPairIssuer.getPublic(), builder.build(), sn, startDate, endDate);
		
		CertificateGenerator cg = new CertificateGenerator();
		
		
		X509Certificate cert = cg.generateCaCertificate(subjectData, issuerData.getX500name(), issuerData.getPrivateKey());
		

		//Moguce je proveriti da li je digitalan potpis sertifikata ispravan, upotrebom javnog kljuca izdavaoca
		cert.verify(keyPairIssuer.getPublic());
		System.out.println("\nValidacija uspesna :)");
	
		KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
		keyStoreWriter.loadKeyStore(null, info.getKeystorepass().toCharArray());
	
		
		
		keyStoreWriter.write(info.getAlias(), keyPairIssuer.getPrivate(), info.getCertpass().toCharArray(), cert);
		String keystorepass = info.getKeystorepass();
		keyStoreWriter.saveKeyStore("./data/keystore.jks",info.getKeystorepass().toCharArray() );
		
		Certificates cer = new Certificates( Long.parseLong(sn), info.getAlias(), true);
		service.addCertificate(cer);
		
		saveToConfig("keystore", info.getKeystorepass());

	
		
		return new MessageWithObj("ok", true, info);
	}
	
	private IssuerData generateSelfCertIssuerData(PrivateKey issuerKey, SubjectInfo info, String uid) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.SURNAME, info.getPrezime());
	    builder.addRDN(BCStyle.GIVENNAME, info.getIme()+" "+info.getPrezime());
	    builder.addRDN(BCStyle.O, info.getKompanija());
	    builder.addRDN(BCStyle.OU, info.getOrgUnit());
	    builder.addRDN(BCStyle.C, "RS");
	    builder.addRDN(BCStyle.CN, info.getIme());
	    builder.addRDN(BCStyle.E, info.getEmail());
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, uid);
		   

		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(issuerKey, builder.build());
	}
	
	@RequestMapping(value = "/certgen",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public  MessageWithObj generisanjeNovogCerta(@RequestBody SubjectInfo info) throws ParseException, InvalidKeyException, CertificateException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException{
		return null;
	}
	@RequestMapping(value = "/certvalidlist",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public  ArrayList<String> listaCAcerts() throws CertificateExpiredException, CertificateNotYetValidException{
		
		ArrayList<String> ret = new ArrayList<String>();
		
		KeyStoreWriter ksw = new KeyStoreWriter();
		KeyStoreReader ksr = new KeyStoreReader();
		X509Certificate xcert=null;
		ksw.loadKeyStore("./data/keystore.jks", loadKeyStorePass().toCharArray());
		
		Enumeration<String> aliases = ksw.getAlias();
		ArrayList<String> certList = new ArrayList<String>();
		while (aliases.hasMoreElements()) {
			String show = aliases.nextElement();
			certList.add(show);
		}
		
		
		java.security.cert.Certificate cert=null;
		String alias = "";
		
		for(int i=0; i<certList.size(); i++){//TODO cuvati na nekom mestu valjda
			cert = ksr.readCertificate("./data/keystore.jks", loadKeyStorePass(), certList.get(i));
			 xcert = (X509Certificate)cert;
			
			if(xcert.getBasicConstraints()!=-1){
				alias = certList.get(i);
				ret.add(alias);
				
			}
		}

	return ret;
	}
	
	@RequestMapping(value = "/generateCAS",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public  MessageWithObj generateCAS(@RequestBody SubjectInfo info) throws ParseException, InvalidKeyException, CertificateException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException  {
	
		
		//Serijski broj sertifikata
		String sn= Long.toString(System.currentTimeMillis());
		//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
		SubjectData subjectData=generateSubjectData(info);
		//dopuniti na osnovu alijasa
		
		KeyStoreReader ksr = new KeyStoreReader();
		java.security.cert.Certificate jcert = ksr.readCertificate("./data/keystore.jks",
				"admin",info.getSigner());
		
		
		
		X509Certificate xcert = (X509Certificate)jcert;
		
		
		X500Name x500Issuer = new JcaX509CertificateHolder(xcert).getSubject();
		
		
		
		KeyPair keyPairIssuer = generateKeyPair();
		
		subjectData.setPublicKey(keyPairIssuer.getPublic());
		//IssuerData issuerData=new IssuerData(keyPairIssuer.getPrivate(), x500Issuer);
		
		CertificateGenerator cg = new CertificateGenerator();
		
		PrivateKey issuerPrivate = ksr.readPrivateKey("./data/keystore.jks", loadKeyStorePass(), info.getSigner(), "private");
		
		X509Certificate cert = cg.generateCaCertificate(subjectData, x500Issuer,
				issuerPrivate);

		
		//Moguce je proveriti da li je digitalan potpis sertifikata ispravan, upotrebom javnog kljuca izdavaoca

		try{
			cert.verify(xcert.getPublicKey());
			System.out.println("\nValidacija uspesna :)");
		}catch(Exception ee){
			System.out.println("Validacija neuspesna");
		}
		
		
		KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
		//SIFRU UNOSITI
		keyStoreWriter.loadKeyStore("./data/keystore.jks", loadKeyStorePass().toCharArray());
	
		String pkpass  = "private";
		keyStoreWriter.write(info.getAlias(), keyPairIssuer.getPrivate(), pkpass.toCharArray(), cert);
		String keystorepass = "admin";
		keyStoreWriter.saveKeyStore("./data/keystore.jks",loadKeyStorePass().toCharArray() );
		Certificates cer =null;
		
		
		if(cert.getBasicConstraints()!=-1)
		 cer = new Certificates( Long.parseLong(sn), info.getAlias(),true);
		else
		 cer = new Certificates( Long.parseLong(sn), info.getAlias(),true);
		service.addCertificate(cer);
		
		
		return null;
	}
	
	@RequestMapping(value = "/csr",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
	public MessageWithObj  certificateSigningRequest(@RequestBody CsrRequest requestInfo) throws OperatorCreationException, InvalidKeyException, NoSuchAlgorithmException, IOException, CertificateEncodingException{
		String signer = requestInfo.getSigner();
		String csrString = requestInfo.getCsr();
		Long days = Long.parseLong(requestInfo.getDays());
	
		 PKCS10CertificationRequest csr = convertPemToPKCS10CertificationRequest(csrString);
		 //rand
		 X500Name x500NameSubject = csr.getSubject();
		 
		 JcaPKCS10CertificationRequest jcaCertRequest =
			       new JcaPKCS10CertificationRequest(csr.getEncoded()).setProvider("BC");
		
		 KeyStoreReader ksr = new KeyStoreReader();
		
			java.security.cert.Certificate jcert = ksr.readCertificate("./data/keystore.jks",
					loadKeyStorePass(),signer);
			
			X509Certificate xcert = (X509Certificate)jcert;
			
			//javni kljuc iz pem-a
		PublicKey subjectPublicKey = jcaCertRequest.getPublicKey();

			X500Name x500Issuer = new JcaX509CertificateHolder(xcert).getSubject();
			
			//Javni kljuc izdavaoca
			PublicKey authorityPublicKey = xcert.getPublicKey();
			
		
			KeyPair keyPairIssuer = generateKeyPair();
			//TODO: DA LI SE GENERISE NOVI ILI SE KORISTI STARI 
			IssuerData issuerData=new IssuerData(keyPairIssuer.getPrivate(), x500Issuer);
		
			//Serijski broj sertifikata
			String sn= Long.toString(System.currentTimeMillis());
			SubjectData sdata = new SubjectData();
			sdata.setPublicKey(subjectPublicKey);
			sdata.setX500name(x500NameSubject);
			sdata.setSerialNumber(sn);
			sdata.setStartDate(new Date(System.currentTimeMillis()));
		
			sdata.setEndDate(new Date(System.currentTimeMillis()+days*86400000));
			
			CertificateGenerator cg = new CertificateGenerator();
			//za kreiranje izgenerisani novi kljucevi i prosledjen privatni kljuc izdavaoca radi potpisa
			
			
		
			PrivateKey issuerPrivate = ksr.readPrivateKey("./data/keystore.jks",loadKeyStorePass(), signer, "private");

			X509Certificate cert = cg.generateEndCertificate(sdata, issuerData, issuerPrivate, subjectPublicKey);
		
			
			
			KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
			//SIFRU UNOSITI
			keyStoreWriter.loadKeyStore("./data/keystore.jks", loadKeyStorePass().toCharArray());
		
			String certpass  = "private";
			
			keyStoreWriter.write("csrcert", issuerPrivate, certpass.toCharArray(), cert);
			String keystorepass = "admin";
			keyStoreWriter.saveKeyStore("./data/keystore.jks",loadKeyStorePass().toCharArray() );
			Certificates cer =null;
			
			
			if(cert.getBasicConstraints()!=-1)
			 cer = new Certificates(Long.parseLong(sn), "csrcert",true);
			else
			 cer = new Certificates( Long.parseLong(sn), "csrcert",true);
			service.addCertificate(cer);
			
			
		
		 

	
	
		return  new MessageWithObj("OK", true, csr.toString());
	}
	
	
    private PKCS10CertificationRequest convertPemToPKCS10CertificationRequest(String pem) {
       // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        PKCS10CertificationRequest csr = null;
        ByteArrayInputStream pemStream = null;
        try {
            pemStream = new ByteArrayInputStream(pem.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
          //  LOG.error("UnsupportedEncodingException, convertPemToPublicKey", ex);
        }

        Reader pemReader = new BufferedReader(new InputStreamReader(pemStream));
        PEMParser pemParser = new PEMParser(pemReader);

        try {
            Object parsedObj = pemParser.readObject();

            System.out.println("PemParser returned: " + parsedObj);

            if (parsedObj instanceof PKCS10CertificationRequest) {
                csr = (PKCS10CertificationRequest) parsedObj;

            }
        } catch (IOException ex) {
           // LOG.error("IOException, convertPemToPublicKey", ex);
        }

        return csr;
    }
	
    private String getX500Field(String asn1ObjectIdentifier, X500Name x500Name) {
        RDN[] rdnArray = x500Name.getRDNs(new ASN1ObjectIdentifier(asn1ObjectIdentifier));
        String retVal = null;
        for (RDN item : rdnArray) {
            retVal = item.getFirst().getValue().toString();
        }

        return retVal;
    }

	
	
	
	
	
	@RequestMapping(value = "/generateEndCert",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public  MessageWithObj generateEndCertificate(@RequestBody SubjectInfo info) throws ParseException, InvalidKeyException, CertificateException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException  {
	
		
		//Serijski broj sertifikata
		String sn= Long.toString(System.currentTimeMillis());
		//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
		SubjectData subjectData=generateSubjectData(info);
		//dopuniti na osnovu alijasa
		
		KeyStoreReader ksr = new KeyStoreReader();
		//vadimo iz keystora certifikat koji ce biti issuer i potpisivac end certifikatu
		//TODO: Sifra keystora treba se unositi rucno
		java.security.cert.Certificate jcert = ksr.readCertificate("./data/keystore.jks",
				loadKeyStorePass(),info.getSigner());
		

		
		
		X509Certificate xcert = (X509Certificate)jcert;
	//	xcert.getExtensionValue(oid)
		
		X500Name x500Issuer = new JcaX509CertificateHolder(xcert).getSubject();
		
		//Javni kljuc izdavaoca
		PublicKey authorityPublicKey = xcert.getPublicKey();
		
	
		KeyPair keyPairIssuer = generateKeyPair();
		IssuerData issuerData=new IssuerData(keyPairIssuer.getPrivate(), x500Issuer);
		subjectData.setPublicKey(keyPairIssuer.getPublic());
		
		CertificateGenerator cg = new CertificateGenerator();
		//za kreiranje izgenerisani novi kljucevi i prosledjen privatni kljuc izdavaoca radi potpisa
		
		
	
		PrivateKey issuerPrivate = ksr.readPrivateKey("./data/keystore.jks", loadKeyStorePass(), info.getSigner(), "private");
		
		X509Certificate cert = cg.generateEndCertificate(subjectData, issuerData, issuerPrivate, subjectData.getPublicKey());
		
		
		
		
		
		System.out.println("\n===== Podaci o izdavacu sertifikata =====");
		System.out.println(cert.getIssuerX500Principal().getName());
		System.out.println("\n===== Podaci o vlasniku sertifikata =====");
		System.out.println(cert.getSubjectX500Principal().getName());
		System.out.println("\n===== Sertifikat =====");
		System.out.println("-------------------------------------------------------");
		System.out.println(cert);
		System.out.println("-------------------------------------------------------");
		
		//Moguce je proveriti da li je digitalan potpis sertifikata ispravan, upotrebom javnog kljuca izdavaoca
		//NA DRUGI NACIM..iz ekstenzije izvaditi javni kljuc 
		//cert.verify(keyPairIssuer.getPublic());
		
		try{
			cert.verify(authorityPublicKey);
			System.out.println("\nValidacija uspesna..");
		}catch(Exception eee){
			System.out.println("\nValidacija neususpesna :)");
		}

		KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
		//SIFRU UNOSITI
		keyStoreWriter.loadKeyStore("./data/keystore.jks", loadKeyStorePass().toCharArray());
	
		String pkpass  = "private";
		keyStoreWriter.write(info.getAlias(), keyPairIssuer.getPrivate(), pkpass.toCharArray(), cert);
		String keystorepass = "admin";
		keyStoreWriter.saveKeyStore("./data/keystore.jks",loadKeyStorePass().toCharArray() );
		Certificates cer =null;
		
		//cuvanje u bazi
		if(info.getCa().equals("true"))
			cer = new Certificates( Long.parseLong(sn), info.getAlias(),true);
		else
			cer = new Certificates(Long.parseLong(sn), info.getAlias(),true);
		service.addCertificate(cer);
		

		
		
		return null;
	}
	
	private SubjectData generateSubjectData(SubjectInfo info) throws ParseException {

		
		
		Date startDate = info.getStartDate();
		Date endDate = info.getEndDate();
		//Serijski broj sertifikata
		String sn= Long.toString(System.currentTimeMillis());
		//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
		
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.SURNAME, info.getPrezime());
		builder.addRDN(BCStyle.GIVENNAME, info.getIme()+" "+info.getPrezime());
		builder.addRDN(BCStyle.O, info.getKompanija());
		builder.addRDN(BCStyle.E, info.getIme());
		builder.addRDN(BCStyle.OU, info.getOrgUnit());
		builder.addRDN(BCStyle.C, "RS");
		builder.addRDN(BCStyle.E, info.getEmail());
		//UID (USER ID) je ID korisnika
		builder.addRDN(BCStyle.UID, Long.toString(System.currentTimeMillis()));

		return new SubjectData(null, builder.build(), sn, startDate, endDate);
	
	}
	private KeyPair generateKeyPair() {
        try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	
	private void saveToConfig(String par1, String par2){
		Properties prop = new Properties();
		OutputStream output = null;
		try {

			output = new FileOutputStream("config.properties");

			// set the properties value
			prop.setProperty(par1,par2);
			

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
	}
	
	private String loadKeyStorePass(){
		Properties prop = new Properties();
    	InputStream input = null;

    	try {

    		input = new FileInputStream("config.properties");

    		// load a properties file
    		prop.load(input);

    	return prop.getProperty("keystore");
    	

    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null){
        		try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	}
        }
		return null;

	}
	
	
	

}
