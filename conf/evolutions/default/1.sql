# --- !Ups

create table users (
  id int(6) unsigned auto_increment primary key,
  first_name varchar(30) not null,
  last_name varchar(30) not null,
  age int not null,
  email varchar(50),
  creation_date timestamp default current_timestamp on update current_timestamp
);

# --- !Downs

drop table users if exists;
