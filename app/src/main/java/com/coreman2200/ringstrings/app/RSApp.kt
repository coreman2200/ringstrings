package com.coreman2200.ringstrings.app

import android.app.Application
import com.coreman2200.ringstrings.app.di.ApplicationComponent
import com.coreman2200.ringstrings.app.di.UtilsModule

/**
 * RSApp
 * description
 *
 *
 * Created by Cory Higginbottom on 3/4/16
 * http://github.com/coreman2200
 *
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
class RSApp : Application() {
    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        /*
         'ApplicationComponent' is created including all data every associated component needs.
         Specifically, 'modules' parameters refer to those which demand external variables (mostly
         'Context' instances).
         */
        appComponent = DaggerApplicationComponent.factory().create(modules = UtilsModule(ctx = this))
    }
}