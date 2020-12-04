--createdb choco;

create table if not exists chocolate (
    id serial primary key not null,
    name text,
    qty int
);


insert into chocolate(name, qty) values ('Bar One', 5);
insert into chocolate(name, qty) values ('Lunch Bar', 5);
insert into chocolate(name, qty) values ('Cardbury', 5);
insert into chocolate(name, qty) values ('Kitkat', 5);
