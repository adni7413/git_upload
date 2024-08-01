

create table TW_TOWN 
(
TOWN_SERIAL varchar2(20 byte) primary key
, TOWN nvarchar2(20)
, TOWN_OFFICE nvarchar2(20) 
, OFFICE_TEL varchar2(20 byte)
);

create table TW_REGION 
(
BUILDING nvarchar2(30) 
,TOWN_SERIAL varchar2(20 byte) references TW_TOWN(TOWN_SERIAL)
, TOWN nvarchar2(20) 
, TOWN_OFFICE nvarchar2(20) 
, OFFICE_TEL varchar2(20 byte) );


create table BUILDING 
(
  BUILDING_ADDRESS nvarchar2(30) 
, PEOPLE_NUM number(*, 0) 
, FLOOR_NUM number(*, 0)
,TOWN_SERIAL varchar2(20 byte) references TW_TOWN(TOWN_SERIAL)
,BRANCH_SERIAL varchar2(20) references POLICE(BRANCH_SERIAL)
);


create table POLICE 
(
  BRANCH_SERIAL varchar2(20) primary key
, BRANCH nvarchar2(20) 
, BRANCH_ADDRESS nvarchar2(20) 
, BRANCH_TEL varchar2(20)
);


insert into TW_TOWN values ( 'C001','大埔里','竹南鎮公義路1035號','03 758 1072');
insert into TW_TOWN values ( 'C002','竹南里','竹南鎮竹南里中山路103號','037-472735');
insert into TW_TOWN values ( 'C003','山佳里','竹南鎮山佳里國光街 14 號','037-614186');
insert into TW_TOWN values ( 'C004','埔頂里','後龍鎮埔頂里中興路136-1號','037-724839');
insert into TW_TOWN values ( 'C005','綠苗里','苗栗市綠苗里中正路 766 號','037-333240' );
insert into TW_TOWN values ( 'C006','民族里','民族里民族路96號','037-660001');
insert into TW_TOWN values ( 'C007','忠孝里','忠孝里光大街82號','037-661145');
insert into TW_TOWN values ( 'C008','信義里','信義里信義路53巷1號','037-616072');


insert into TW_REGION values ( '公寓','C001','大埔里','竹南鎮公義路1035號','03 758 1072') ;
insert into TW_REGION values( '大樓','C002','竹南里','竹南鎮竹南里中山路103號','037-472735');
insert into TW_REGION values( '大樓','C003','山佳里','竹南鎮山佳里國光街 14 號','037-614186' );
insert into TW_REGION values( '公共設施','C004','埔頂里','後龍鎮埔頂里中興路136-1號','037-724839' );
insert into TW_REGION values( '公寓','C005','綠苗里','苗栗市綠苗里中正路 766 號','037-333240' );
insert into TW_REGION values( '大樓','C005','綠苗里','苗栗市綠苗里中正路 766 號','037-333240' );
insert into TW_REGION values( '公共設施','C006','民族里','民族里民族路96號','037-660001' );
insert into TW_REGION values( '私營單位','C007','忠孝里','忠孝里光大街82號','037-661145' );
insert into TW_REGION values( '公寓','C008','信義里','信義里信義路53巷1號','037-616072');
insert into TW_REGION values( '私營單位','C008','信義里','信義里信義路53巷1號','037-616072' );

insert into POLICE values ( 'M001','竹南分局','苗栗縣竹南鎮民族街72號','03 747 4796');
insert into POLICE values ( 'M002','苗栗分局','苗栗縣苗栗市金鳳街109號','03 732 0059');
insert into POLICE values ( 'M003','頭份分局'	,'苗栗縣頭份市中興路503號','03 766 3004');


insert into BUILDING values ( '苗栗縣竹南鎮中埔街20號',100,1,'C001','M001');
insert into BUILDING values ( '苗栗縣竹南鎮和平街79號',3142,1,'C002','M001');
insert into BUILDING values ( '苗栗縣竹南鎮龍山路三段142號',1072,1,	'C003','M001');
insert into BUILDING values ( '苗栗縣後龍鎮中華路1498號',32,1,	'C004',	'M001');
insert into BUILDING values ( '苗栗縣苗栗市米市街80號',106,1,'C005','M002');
insert into BUILDING values ( '苗栗縣苗栗市光復路117號',26,1,'C005','M002');
insert into BUILDING values ( '苗栗縣苗栗市大同路53號',128,2,'C005','M002');
insert into BUILDING values ( '苗栗縣苗栗市博愛街109號',2038,2,'C005',	'M002');
insert into BUILDING values ( '苗栗縣頭份市民族里和平路102號',353,1,'C006','M003');
insert into BUILDING values ( '苗栗縣頭份市忠孝忠孝一路69號',501,1,'C007','M003');
insert into BUILDING values ( '苗栗縣頭份市信義里中正路65號',194,1,'C008','M003');
insert into BUILDING values ( '苗栗縣頭份市信義里中正路116號',78,1,'C008','M003');

題目4-1
select  distinct DD.BRANCH, DD.BRANCH_TEL
from POLICE DD  inner join BUILDING BU on (BU.BRANCH_SERIAL=DD.BRANCH_SERIAL) and ( BU.PEOPLE_NUM > 1000);

題目4-2
select  distinct DD.BRANCH, DD.BRANCH_TEL,COUNT(DD.BRANCH)
from POLICE DD inner join BUILDING BU on (BU.BRANCH_SERIAL=DD.BRANCH_SERIAL) and ( BU.PEOPLE_NUM > 1000)
group by BRANCH, BRANCH_TEL;


題目4-3
select BRANCH,BRANCH_TEL,BUILDING, BUILDING_ADDRESS,count(BRANCH)
from BUILDING BU  join TW_REGION TW on ( BU.TOWN_SERIAL = TW.TOWN_SERIAL )
                join  POLICE PP on ( BU.BRANCH_SERIAL = PP.BRANCH_SERIAL )
where PEOPLE_NUM > 1000
group by BRANCH,BRANCH_TEL,BUILDING,BUILDING_ADDRESS;

題目4-4
select TOWN,BUILDING_ADDRESS,PEOPLE_NUM,BRANCH,BRANCH_TEL
from BUILDING BU  join TW_TOWN TW on ( BU.TOWN_SERIAL = TW.TOWN_SERIAL )
                join  POLICE PP on ( BU.BRANCH_SERIAL = PP.BRANCH_SERIAL )
where BUILDING_ADDRESS  LIKE '%中%';

題目4-5
select BUILDING, TOWN,TOWN_OFFICE, BUILDING_ADDRESS,PEOPLE_NUM
from BUILDING BU  join TW_REGION TW on ( BU.TOWN_SERIAL = TW.TOWN_SERIAL )
                join  POLICE PP on ( BU.BRANCH_SERIAL = PP.BRANCH_SERIAL )
WHERE BUILDING IN ('公寓','大樓')

題目5-1

create table BUILDING_BACKUP 
(
  BUILDING_ADDRESS nvarchar2(30) 
, PEOPLE_NUM number(*, 0) 
, FLOOR_NUM number(*, 0)
,TOWN_SERIAL varchar2(20 BYTE) references TW_TOWN(TOWN_SERIAL)
,BRANCH_SERIAL varchar2(20) references POLICE(BRANCH_SERIAL)
);
insert into BUILDING_BACKUP select * from BUILDING 

update BUILDING_BACKUP
set PEOPLE_NUM = 5000
where BUILDING_ADDRESS = '苗栗縣竹南鎮和平街79號'

select BUILDING_ADDRESS, PEOPLE_NUM
from BUILDING_BACKUP

題目5-2
delete from BUILDING_BACKUP where PEOPLE_NUM < 1000;

select BUILDING_ADDRESS, PEOPLE_NUM
from BUILDING_BACKUP
