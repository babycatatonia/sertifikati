insert into rola (naziv) values ('Admin');
insert into rola (naziv) values ('PravnoLice');
insert into rola (naziv) values ('MenadzerSistema');

insert into privilegija (naziv) values ('Banka:Dodaj');
insert into privilegija (naziv) values ('Banka:Izmeni');
--insert into privilegija (naziv) values ('Banka:Obrisi');
insert into privilegija (naziv) values ('Banka:IzlistajPretrazi');
insert into privilegija (naziv) values ('SelfSign');

insert into rola_privilegija values (1, 1);
insert into rola_privilegija values (1, 2);
insert into rola_privilegija values (1, 3);
insert into rola_privilegija values (1, 4);

insert into korisnik (tip_korisnika, ime, prezime, rola_id, email, sifra, username) values ('A' ,'Sima1', 'Simic1', 1, 'k1@k1.com', '$2a$12$/eDioOpiKrGQnDv53hPxIe8XDCJWP/Xe1fF6E5IrTVxuBozu9EX9G', 'kor');