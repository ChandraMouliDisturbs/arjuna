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

from .basepage import WPBasePage
from .dashboard import DashboardPage

class HomePage(WPBasePage):

    def login(self, user, pwd):
        self.element("login").text = user
        self.element("password").text = pwd
        self.element("submit").click()

        self.element("view_site").wait_until_visible()
        return DashboardPage(self)

    def login_with_default_creds(self):
        user, pwd = self.config.get_user_option_value("wp.users.admin").split_as_str_list()
        return self.login(user, pwd)