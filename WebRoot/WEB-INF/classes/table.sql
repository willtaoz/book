drop table admin;
drop table bookpic;
drop table book;
drop table category;
create table admin(
	id varchar2(32) primary key,
	name varchar2(32),
	pwd varchar2(32)
);
create table category(
	id varchar2(32) primary key,
	name varchar2(32)
);
insert into category values(sys_guid(),'历史类');
insert into category values(sys_guid(),'小说类');

create table book(
	id varchar2(32) primary key ,
	name varchar2(32),
	price number(5,2),
	author varchar2(32),
	publishdate date,
	status varchar2(5),
	categoryid varchar2(32),
	foreign key(categoryid) references category(id)
);
insert into book 
select sys_guid(),'西游记5',111,'吴承恩',sysdate,'0',id from 
category where name='小说类';
insert into book 
select sys_guid(),'西游记7',111,'吴承恩',sysdate,'0',id from 
category where name='小说类';
insert into book 
select sys_guid(),'西游记9',111,'吴承恩',sysdate,'0',id from 
category where name='小说类';
insert into book 
select sys_guid(),'水浒',111,'施耐庵',sysdate,'0',id from 
category where name='小说类';
insert into book 
select sys_guid(),'三国',111,'罗贯中',sysdate,'0',id from 
category where name='历史类';
insert into book 
select sys_guid(),'红楼梦',111,'曹雪芹',sysdate,'0',id from 
category where name='小说类';
insert into book 
select sys_guid(),'西游记1',111,'吴承恩',sysdate,'0',id from 
category where name='小说类';
insert into book 
select sys_guid(),'西游记2',111,'吴承恩',sysdate,'0',id from 
category where name='小说类';

create table bookpic(
	id varchar2(32) primary key,
	savepath varchar2(200),
	showname varchar2(100),
	fm varchar2(5),
	bookid varchar2(32),
	foreign key(bookid) references book(id)
);
select * from category;
select * from bookpic;