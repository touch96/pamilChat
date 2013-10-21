create table users (
    id                      varchar(100),
    pw                      varchar(100),
    nickname               varchar(200),
    osKbn               varchar(100),
	createDt            date,
	token                varchar(100) not null,
    constraint pk_parent primary key (token));