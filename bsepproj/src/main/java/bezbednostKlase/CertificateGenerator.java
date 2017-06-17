package bezbednostKlase;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.asn1.x509.Time;
import org.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.X509KeyUsage;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;



public class CertificateGenerator {
	public CertificateGenerator() {}
	
		public X509Certificate generateCaCertificate(SubjectData subjectData, X500Name issuerData, PrivateKey pkSigner) throws NoSuchAlgorithmException {
			try {
				//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
				//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
				//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
				JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
				//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
				builder = builder.setProvider("BC");
	
				//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
				ContentSigner contentSigner = builder.build(pkSigner);
	
	
				//Postavljaju se podaci za generisanje sertifiakta
		
				X509v3CertificateBuilder certGen =
						new JcaX509v3CertificateBuilder(
								
								issuerData,
						new BigInteger(subjectData.getSerialNumber()),
						subjectData.getStartDate(),
						subjectData.getEndDate(),
						subjectData.getX500name(),
						subjectData.getPublicKey())
						
						.addExtension(
						        new ASN1ObjectIdentifier("2.5.29.19"), 
						        false,
						        new BasicConstraints(true))
						.addExtension(
						                new ASN1ObjectIdentifier("2.5.29.15"),
						                true,
						                (ASN1Encodable) new X509KeyUsage(
						                   X509KeyUsage.digitalSignature |
						                   X509KeyUsage.nonRepudiation   |
						                   X509KeyUsage.keyEncipherment  |
						                   X509KeyUsage.dataEncipherment|
						                   X509KeyUsage.keyCertSign|
						                   X509KeyUsage.cRLSign));
				
	
	
				//Generise se sertifikat
				X509CertificateHolder certHolder = certGen.build(contentSigner);
	
				//Builder generise sertifikat kao objekat klase X509CertificateHolder
				//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
				JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
				certConverter = certConverter.setProvider("BC");
	
				//Konvertuje objekat u sertifikat
				return certConverter.getCertificate(certHolder);
			} catch (CertificateEncodingException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (OperatorCreationException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	
	
	
	public X509Certificate generateEndCertificate(SubjectData subjectData,
			IssuerData issuerData, PrivateKey signer, PublicKey authorityPubKey) throws NoSuchAlgorithmException {
		
		try {
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");
		
			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(signer);



			//Postavljaju se podaci za generisanje sertifiakta
			
			X509v3CertificateBuilder certGen =
					new JcaX509v3CertificateBuilder(
							
					issuerData.getX500name(),
					new BigInteger(subjectData.getSerialNumber()),
					subjectData.getStartDate(),
					subjectData.getEndDate(),
					subjectData.getX500name(),
					subjectData.getPublicKey())
					.addExtension(
					        new ASN1ObjectIdentifier("2.5.29.19"), 
					        false,
					        new BasicConstraints(false))
					.addExtension(
					                new ASN1ObjectIdentifier("2.5.29.15"),
					                true,
					                (ASN1Encodable) new X509KeyUsage(
					                   X509KeyUsage.digitalSignature |
					                   X509KeyUsage.nonRepudiation   |
					                   X509KeyUsage.keyEncipherment  |
					                   X509KeyUsage.dataEncipherment));
		
			
		//Generise se sertifikat
		X509CertificateHolder certHolder = certGen.build(contentSigner);

			//Builder generise sertifikat kao objekat klase X509CertificateHolder
			//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			//Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}
