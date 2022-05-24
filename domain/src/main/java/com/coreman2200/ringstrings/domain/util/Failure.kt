package com.coreman2200.ringstrings.domain.util

/**
 * Failure
 * description
 *
 * Created by Cory Higginbottom on 5/22/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

sealed class Failure(var msg: String = "n/a") {
    class InputParamsError(msg: String = "Parameters cannot be null") : Failure(msg = msg)
    class ServerError(msg: String = "Server error") : Failure(msg = msg)
    class NoData(msg: String = "No data") : Failure(msg = msg)
    class NoConnection(msg: String = "No connection") : Failure(msg = msg)
    class Unknown(msg: String = "Unknown error") : Failure(msg = msg)
}