 create table singer(
    id int not null auto_increment,
    first_name varchar(60) not null,
    last_name varchar(40) not null,
    birth_date date,
    unique uq_singer_1 (first_name,last_name),
    primary key(id));

 create table album(
   id int not null auto_increment,
   singer_id int not null,
   title varchar(100) not null,
   release_date date,
   unique uq_singer_album_q (singer_id,title),
   primary key(id),
   constraint fk_album foreign key(singer_id) references singer(id));

 insert into singer(first_name, last_name, birth_date) VALUES ('John','Mayer','1977-10-16');
 insert into singer(first_name, last_name, birth_date) VALUES ('Eric','Clapton','1945-03-30');
 insert into singer(first_name, last_name, birth_date) VALUES ('John','Butler','1975-04-01');

 INSERT INTO album(singer_id, title, release_date)
     VALUES (1,'The Search For Everything','2017-01-20');
 INSERT INTO album(singer_id, title, release_date)
     VALUES (1,'Battle Studies','2009-11-17');
 INSERT INTO album(singer_id, title, release_date)
 VALUES (2,'From the Cradle','1994-09-13');
