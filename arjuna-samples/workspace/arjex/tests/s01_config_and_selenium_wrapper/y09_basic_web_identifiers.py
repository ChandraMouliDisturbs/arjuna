'''
This file is a part of Arjuna
Copyright 2015-2020 Rahul Verma

Website: www.RahulVerma.net

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
'''

from commons import *
from arjuna import *

init_arjuna()
wordpress = create_wordpress_app()

# The following code is for user name field.
# Html of user name: <input type="text" name="log" id="user_login" class="input" value="" size="20">
element = wordpress.ui.element(With.id("user_login"))
print(element.source.content.root)

element = wordpress.ui.element(With.name("log"))
print(element.source.content.root)

element = wordpress.ui.element(With.class_name("input"))
print(element.source.content.root)

element = wordpress.ui.element(With.tag_name("input"))
print(element.source.content.root)

# The following options are for 
# Html of link: <a href="http://192.168.56.103/wp-login.php?action=lostpassword" title="Password Lost and Found">Lost your password?</a>
element = wordpress.ui.element(With.link_text("Lost your password?"))
print(element.source.content.root)

element = wordpress.ui.element(With.link_ptext("password"))
print(element.source.content.root)

wordpress.quit()
