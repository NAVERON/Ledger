
-- 创建数据库用户 
create user open with password 'wangyulong';
grant all privileges on database ledger to open;
-- 关于postgresql 的权限系统需要了解  schema --> public user --> role 

create table if not exists business_record (
    id serial not null primary key, 
    user_id bigint not null, 
    amount money not null default 0, 
    record_time timestamptz default current_timestamp 
);

create table if not exists role_permission (
    id serial not null primary key, 
    role_type char(20) not null default 'ANONYMITY', 
    description varchar(100), 
    permissions varchar(500) 
);

create table if not exists user_acount (
    id serial not null primary key, 
    user_name char(50) default '', 
    password char(50) default '', 
    email_address varchar(100), 
    phone_number varchar(100), 
    role_type char(20) not null default 'ANONYMITY', 
    role_id bigint 
);


-- 如何创建更新时可以自动更新updatetime 
-- https://x-team.com/blog/automatic-timestamps-with-postgresql/ 

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();  -- 这里的update_at 修改为自己需要修改的column 
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE todos (
  id SERIAL NOT NULL PRIMARY KEY,
  content TEXT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),  -- // === update 
  completed_at TIMESTAMPTZ
);

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON todos
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();










