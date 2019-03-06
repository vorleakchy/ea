USING SECURITY GROUPS WITH PERMISSIONS  && RULES

RULES are evaluated based on XML config of CustomPermissionEvaluator.
CustomPermissionEvaluator determines the target object {asset} [e.g. Comment]
 Looks up policy / Rules for asset    
 EVALUATES rules against User, Action, asset environment

NOTE FOR RULE -- Owner & Central Time Zone == SUCCESS

_________________
SETUP Follow similar to "other" security examples
______________________________

ROOT Application security...
NO Web support...Just using CORE Security.

CUSTOM Authentication Manager

Create groups {ADMIN, USER}  
Add PERMISSIONS to groups: CRUD & LIST to ADMIN; UPDATE READ & LIST to USER

Create Sean -- assign to group USER
Create Bill -- assign to group ADMIN
Create Pete -- assign to group USER

Member FindAll has permission LIST which is BOTH Groups
Member Update has permission UPDATE which is ADMIN Group
 
code FIRST tries to access WITHOUT logging in -- ANONYMOUS User - Access Denied...

Login ... with a user [Sean,Bill or Pete] ALL has permission to "LIST" all Users

ONLY Pete has permission to UPDATE comment based on Ownership Rule AND Time Zone Rule
You can TOGGLE Time Zone Rule by changing the test in CommentEvaluatorDto update method
ONLY Bill[ADMIN] has permission to  DELETE

