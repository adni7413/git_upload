
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
, TOWN_SERIAL varchar2(20 byte) references TW_TOWN(TOWN_SERIAL)
, TOWN nvarchar2(20) 
, TOWN_OFFICE nvarchar2(20) 
, OFFICE_TEL varchar2(20 byte) );


create table BUILDING 
(
  BUILDING_ADDRESS nvarchar2(30) 
, PEOPLE_NUM number(*, 0) 
, FLOOR_NUM number(*, 0)
, TOWN_SERIAL varchar2(20 byte) references TW_TOWN(TOWN_SERIAL)
, BRANCH_SERIAL varchar2(20) references POLICE(BRANCH_SERIAL)
);


create table POLICE 
(
  BRANCH_SERIAL varchar2(20) primary key
, BRANCH nvarchar2(20) 
, BRANCH_ADDRESS nvarchar2(20) 
, BRANCH_TEL varchar2(20)
);


insert into TW_TOWN values ( 'C001','�j�H��','�˫n���q��1035��','03 758 1072');
insert into TW_TOWN values ( 'C002','�˫n��','�˫n��˫n�����s��103��','037-472735');
insert into TW_TOWN values ( 'C003','�s�Ψ�','�˫n��s�Ψ������ 14 ��','037-614186');
insert into TW_TOWN values ( 'C004','�H����','���s��H����������136-1��','037-724839');
insert into TW_TOWN values ( 'C005','��]��','�]�ߥ���]�������� 766 ��','037-333240' );
insert into TW_TOWN values ( 'C006','���ڨ�','���ڨ����ڸ�96��','037-660001');
insert into TW_TOWN values ( 'C007','������','���������j��82��','037-661145');
insert into TW_TOWN values ( 'C008','�H�q��','�H�q���H�q��53��1��','037-616072');


insert into TW_REGION values ( '���J','C001','�j�H��','�˫n���q��1035��','03 758 1072') ;
insert into TW_REGION values( '�j��','C002','�˫n��','�˫n��˫n�����s��103��','037-472735');
insert into TW_REGION values( '�j��','C003','�s�Ψ�','�˫n��s�Ψ������14��','037-614186' );
insert into TW_REGION values( '���@�]�I','C004','�H����','���s��H����������136-1��','037-724839' );
insert into TW_REGION values( '���J','C005','��]��','�]�ߥ���]��������766 ��','037-333240' );
insert into TW_REGION values( '�j��','C005','��]��','�]�ߥ���]��������766 ��','037-333240' );
insert into TW_REGION values( '���@�]�I','C006','���ڨ�','���ڨ����ڸ�96��','037-660001' );
insert into TW_REGION values( '�p����','C007','������','���������j��82��','037-661145' );
insert into TW_REGION values( '���J','C008','�H�q��','�H�q���H�q��53��1��','037-616072');
insert into TW_REGION values( '�p����','C008','�H�q��','�H�q���H�q��53��1��','037-616072' );

insert into POLICE values ( 'M001','�˫n����','�]�߿��˫n����ڵ�72��','03 747 4796');
insert into POLICE values ( 'M002','�]�ߤ���','�]�߿��]�ߥ������109��','03 732 0059');
insert into POLICE values ( 'M003','�Y������'	,'�]�߿��Y����������503��','03 766 3004');


insert into BUILDING values ( '�]�߿��˫n���H��20��',100,1,'C001','M001');
insert into BUILDING values ( '�]�߿��˫n��M����79��',3142,1,'C002','M001');
insert into BUILDING values ( '�]�߿��˫n���s�s���T�q142��',1072,1,	'C003','M001');
insert into BUILDING values ( '�]�߿����s���ظ�1498��',32,1,	'C004',	'M001');
insert into BUILDING values ( '�]�߿��]�ߥ��̥���80��',106,1,'C005','M002');
insert into BUILDING values ( '�]�߿��]�ߥ����_��117��',26,1,'C005','M002');
insert into BUILDING values ( '�]�߿��]�ߥ��j�P��53��',128,2,'C005','M002');
insert into BUILDING values ( '�]�߿��]�ߥ��շR��109��',2038,2,'C005',	'M002');
insert into BUILDING values ( '�]�߿��Y�������ڨ��M����102��',353,1,'C006','M003');
insert into BUILDING values ( '�]�߿��Y�������������@��69��',501,1,'C007','M003');
insert into BUILDING values ( '�]�߿��Y�����H�q��������65��',194,1,'C008','M003');
insert into BUILDING values ( '�]�߿��Y�����H�q��������116��',78,1,'C008','M003');

�D��4-1
select  distinct dd.BRANCH as "�Һޤ���", dd.BRANCH_TEL as "�����s���q��"
 from POLICE dd  
 inner join BUILDING bu on bu.BRANCH_SERIAL=dd.BRANCH_SERIAL and  bu.PEOPLE_NUM > 1000;

�D��4-2
select  distinct dd.BRANCH as "�Һޤ���", dd.BRANCH_TEL as "�����s���q��",count(BUILDING_ADDRESS) as "�j��1000�e�H�ƶq���]�I�ƶq"
 from POLICE dd 
 inner join BUILDING bu on bu.BRANCH_SERIAL=dd.BRANCH_SERIAL 
 where bu.PEOPLE_NUM > 1000
 group by BRANCH, BRANCH_TEL;


�D��4-3
select BRANCH as "�Һޤ���" ,BRANCH_TEL as "�����s���q��",BUILDING as "����", BUILDING_ADDRESS as "�����]�I�a�}",count(BUILDING_ADDRESS) as "�j��1000�e�H�ƶq���]�I�ƶq"
 from BUILDING bu  
 left join TW_REGION tw on  bu.TOWN_SERIAL = tw.TOWN_SERIAL 
 left join  POLICE pp on  bu.BRANCH_SERIAL = pp.BRANCH_SERIAL 
 where PEOPLE_NUM > 1000
 group by BRANCH,BRANCH_TEL,BUILDING,BUILDING_ADDRESS;

�D��4-4
select TOWN as "�����O" ,BUILDING_ADDRESS as "�����]�I�a�}" ,PEOPLE_NUM as "�e�H�ƶq",BRANCH as "�Һޤ���",BRANCH_TEL as "�����s���q��"
 from BUILDING bu  
 inner join TW_TOWN tw on bu.TOWN_SERIAL = tw.TOWN_SERIAL 
 inner join  POLICE pp on  bu.BRANCH_SERIAL = pp.BRANCH_SERIAL 
 where BUILDING_ADDRESS like '%��%';

�D��4-5
select  TOWN as "�����O",TOWN_OFFICE as "�����줽�Ǧ�m" , BUILDING_ADDRESS as "�����]�I�a�}",PEOPLE_NUM as "�e�H�ƶq"
 from BUILDING bu  
 left join TW_REGION tw on  bu.TOWN_SERIAL = tw.TOWN_SERIAL 
 left join  POLICE pp on  bu.BRANCH_SERIAL = pp.BRANCH_SERIAL 
 where BUILDING in ('���J','�j��')

�D��5-1

create table BUILDING_BACKUP 
(
  BUILDING_ADDRESS nvarchar2(30) 
, PEOPLE_NUM number(*, 0) 
, FLOOR_NUM number(*, 0)
, TOWN_SERIAL varchar2(20 byte) references TW_TOWN(TOWN_SERIAL)
, BRANCH_SERIAL varchar2(20) references POLICE(BRANCH_SERIAL)
);
insert into BUILDING_BACKUP select * from BUILDING 

update BUILDING_BACKUP
 set PEOPLE_NUM = 5000
 where BUILDING_ADDRESS = '�]�߿��˫n��M����79��'

select BUILDING_ADDRESS, PEOPLE_NUM
 from BUILDING_BACKUP

�D��5-2
delete from BUILDING_BACKUP where PEOPLE_NUM < 1000;

select BUILDING_ADDRESS, PEOPLE_NUM
 from BUILDING_BACKUP
