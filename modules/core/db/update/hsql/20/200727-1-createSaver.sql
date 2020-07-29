create table TASKERWEB_SAVER (
    SAVE_NAME varchar(10) not null,
    UUID varchar(36),
    --
    TASK_ varchar(50),
    DATA_ varchar(255) not null,
    --
    primary key (SAVE_NAME)
);