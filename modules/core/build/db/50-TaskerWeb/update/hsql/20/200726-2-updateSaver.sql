alter table TASKERWEB_SAVER add column DATA_ varchar(255) ^
update TASKERWEB_SAVER set DATA_ = '' where DATA_ is null ;
alter table TASKERWEB_SAVER alter column DATA_ set not null ;
