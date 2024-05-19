
INSERT INTO public.role_tbl(id, name) VALUES (1, 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO public.role_tbl(id, name) VALUES (2, 'PUBLIC') ON CONFLICT DO NOTHING;
--
INSERT INTO public.user_tbl (username,password,active,id)
VALUES ('admin','$2a$10$kRVIV3LBfVOkbX880VRq6uniQLCjtGCJMbGXGFZqTDWC3vb3W94tu',true, 1) ON CONFLICT(username) DO NOTHING;
--        'Emirhan Ay','05365785476','ADMIN','ACTIVE','false','2022-06-01 23:27:03.534');
--INSERT INTO USER_ROLE (user_id,role_id) VALUES ('1','1')


