USING SECURITY GROUPS WITH PERMISSIONS

ROOT Application security...
NO Web support...Just using CORE Security.

CUSTOM Authentication Manager

Create group {ADMIN, USER} assign ROLE_ADMIN & Role_USER to GROUPS
Add PERMISSIONS to groups: CRUD & LIST to ADMIN; READ & LIST to USER

Create Sean -- assign to group USER
Create Bill -- assign to group ADMIN
Create Pete -- assign to group USER

Member FindAll has permission LIST which is in BOTH Groups
Member Update has permission UPDATE which is ONLY ADMIN Group
 
code FIRST tries to access WITHOUT logging in -- ANONYMOUS User - Access Denied...

Login ... with a user [Sean,Bill or Pete] ALL has permission to "LIST" all Users
ONLY Bill has permission to UPDATE



