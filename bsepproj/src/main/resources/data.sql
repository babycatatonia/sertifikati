insert into rola (naziv) values ('Admin');
insert into rola (naziv) values ('Lica');



insert into privilegija (naziv) values ('SelfSign');
insert into privilegija (naziv) values ('CaCert');
insert into privilegija (naziv) values ('EndEntitiyCert');
insert into privilegija (naziv) values ('Download');
insert into privilegija (naziv) values ('Revoke');
insert into privilegija (naziv) values ('Ocsp');
insert into privilegija (naziv) values ('Csr');

insert into rola_privilegija values (1, 1);
insert into rola_privilegija values (1, 2);
insert into rola_privilegija values (1, 5);
insert into rola_privilegija values (1, 4);
insert into rola_privilegija values (1, 6);

insert into rola_privilegija values (2, 3);
insert into rola_privilegija values (2, 7);
insert into rola_privilegija values (2, 4);
insert into rola_privilegija values (2, 6);

insert into korisnik (tip_korisnika, ime, prezime, rola_id, email, sifra, username) values ('A' ,'Sima1', 'Simic1', 1, 'k1@k1.com', '$2a$12$/eDioOpiKrGQnDv53hPxIe8XDCJWP/Xe1fF6E5IrTVxuBozu9EX9G', 'kor1');
insert into korisnik (tip_korisnika, ime, prezime, rola_id, email, sifra, username) values ('L' ,'Sima2', 'Simic2', 2, 'k2@k2.com', '$2a$12$/eDioOpiKrGQnDv53hPxIe8XDCJWP/Xe1fF6E5IrTVxuBozu9EX9G', 'kor2');