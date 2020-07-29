alter table TASKERWEB_SAVER alter column TASK_ rename to TASK___U70675 ^
alter table TASKERWEB_SAVER alter column TASK___U70675 set null ;
alter table TASKERWEB_SAVER add column TASK_ integer ^
update TASKERWEB_SAVER set TASK_ = 0 where TASK_ is null ;
alter table TASKERWEB_SAVER alter column TASK_ set not null ;
