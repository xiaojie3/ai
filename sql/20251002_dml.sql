INSERT INTO ai_users (account, name, password, email, enabled)
VALUES ('admin', '系统管理员', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 'admin@example.com', true);

commit;

insert into ai_permissions (id, name)
values (1, 'read:course');

insert into ai_permissions (id, name)
values (2, 'create:course');

commit;