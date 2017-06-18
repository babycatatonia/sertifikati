insert into rola (naziv) values ('Admin');
insert into rola (naziv) values ('Pravsafsa');

insert into privilegija (naziv) values ('SelfSign');
insert into privilegija (naziv) values ('CA');
insert into privilegija (naziv) values ('5');



insert into rola_privilegija values (1, 1);
insert into rola_privilegija values (1, 2);
insert into rola_privilegija values (1, 3);

insert into korisnik (tip_korisnika, ime, prezime, rola_id, email, sifra, username) values ('A' ,'Sima1', 'Simic1', 1, 'k1@k1.com', '$2a$12$/eDioOpiKrGQnDv53hPxIe8XDCJWP/Xe1fF6E5IrTVxuBozu9EX9G', 'kor');