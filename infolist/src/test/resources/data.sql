insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (1, 'jihun', 10, 'A', 1991, 8, 15);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (2, 'a', 6, 'A', 1995, 10, 21);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (3, 'b', 10, 'B', 1997, 1, 15);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (4, 'c', 11, 'O', 1999, 3, 6);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (5, 'd', 5, 'AB', 2000, 3, 21);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (6, 'e', 8, 'B', 1997, 9,1);

insert into block(`id`, `name`) values (1, 'b');
insert into block(`id`, `name`) values (2, 'e');

update person set block_id = 1 where id = 3;
update person set block_id = 2 where id = 6;