create table sys_menu_meta
(
    id            varchar(64) not null comment '菜单项ID' primary key,
    menu_id       varchar(64) not null comment '菜单ID',
    title         varchar(20) not null comment '路由标题',
    icon          varchar(50) null comment '路由图标',
    showBadge     TINYINT(1)  null comment '是否显示徽章',
    showTextBadge varchar(50) null comment '文本徽章',
    isHide        TINYINT(1)  null comment '是否在菜单中隐藏',
    isHideTab     TINYINT(1)  null comment '是否在标签页中隐藏',
    link          varchar(200) null comment '外部链接',
    isIframe      TINYINT(1)  null comment '是否为iframe',
    keepAlive     TINYINT(1)  null comment '是否缓存',
    isFirstLevel  TINYINT(1)  null comment '是否为一级菜单',
    fixedTab      TINYINT(1)  null comment '是否固定标签页',
    create_by     varchar(64) null comment '创建人',
    create_time   datetime    not null comment '创建时间',
    update_by     varchar(64) null comment '更新人',
    update_time   datetime null comment '更新时间'
) comment '菜单菜单项';