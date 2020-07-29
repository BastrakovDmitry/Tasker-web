alter table TASKERWEB_SOME_DATA alter column DELETED_BY rename to DELETED_BY__U72664 ^
alter table TASKERWEB_SOME_DATA alter column DELETE_TS rename to DELETE_TS__U03684 ^
alter table TASKERWEB_SOME_DATA alter column UPDATED_BY rename to UPDATED_BY__U12896 ^
alter table TASKERWEB_SOME_DATA alter column UPDATE_TS rename to UPDATE_TS__U54794 ^
alter table TASKERWEB_SOME_DATA alter column CREATED_BY rename to CREATED_BY__U07288 ^
alter table TASKERWEB_SOME_DATA alter column CREATE_TS rename to CREATE_TS__U08069 ^
alter table TASKERWEB_SOME_DATA alter column VERSION rename to VERSION__U94443 ^
alter table TASKERWEB_SOME_DATA alter column VERSION__U94443 set null ;
alter table TASKERWEB_SOME_DATA add column UUID varchar(36) ;
alter table TASKERWEB_SOME_DATA add column NAME varchar(10) not null ^
update TASKERWEB_SOME_DATA set NAME = '' where NAME is null ;
alter table TASKERWEB_SOME_DATA alter column NAME set not null ;
