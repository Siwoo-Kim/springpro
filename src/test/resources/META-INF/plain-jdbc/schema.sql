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
