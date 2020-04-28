insert into person(`id`, `name`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (1, 'jihun', 1991, 8, 15);
insert into person(`id`, `name`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (2, 'a', 1995, 10, 21);
insert into person(`id`, `name`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (3, 'b', 1997, 1, 15);
insert into person(`id`, `name`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (4, 'c', 1999, 3, 6);
insert into person(`id`, `name`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (5, 'd', 2000, 3, 21);
insert into person(`id`, `name`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (6, 'e', 1997, 9,1);
insert into person(`id`, `name`, `deleted`) values (7, 'z', true);

/* 현재 테스트가 간단해서 전체 테스트에 대해 data.sql을 사용하지만 나중에 로직이 복잡해지면 데이터를 어떻게 정하냐에 따라 테스트로직들이 에러가 발생한다.
그럴때는 각 테스트마다 데이터를 생성하여 테스트를 하고 롤백하는 방식을 사용한다 */
