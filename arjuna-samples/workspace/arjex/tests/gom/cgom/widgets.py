from arjuna.interact.gui.gom import Widget

class WPBaseWidget(Widget):

    def __init__(self, parent):
        super().__init__(parent)
        self.externalize_guidef(ns_dir="{}_wordpress/widgets".format(self.app.gns_format.lower()))

class LeftNavSideBar(WPBaseWidget):

    @property
    def settings(self):
        from .settings import SettingsPage
        self.element("settings").click()
        return SettingsPage(self.app, self.automator)

class TopNavBar(WPBaseWidget):

    def logout(self):
        url = self.config.get_user_option_value("wp.logout.url").as_str()
        self.browser.go_to_url(url)

        self.element("logout_confirm").click()
        self.element("logout_msg").wait_until_visible()

        from .home import HomePage
        return HomePage(self.app, self.automator)