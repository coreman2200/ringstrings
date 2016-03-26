package com.coreman2200.data.adapter.symbolid;

import com.coreman2200.domain.protos.SymbolIDBundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * AbstractIdBundleAdapter
 * description
 *
 * Created by Cory Higginbottom on 3/10/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

abstract public class AbstractIdBundleAdapter<T> {

    protected final Map<BundleKeys, T> mIdMap;
    protected final SymbolIDBundle mIdBundle;

    protected AbstractIdBundleAdapter(final SymbolIDBundle bundle) {
        mIdBundle = bundle;
        mIdMap = produceIdMap();
    }

    abstract protected Map<BundleKeys, T> produceIdMap();

    public final SymbolIDBundle getIdBundle() {
        return mIdBundle;
    }

    protected static List<Integer> produceIdList(Collection<Enum<? extends Enum<?>>> enumList) {
        List<Integer> list = new ArrayList<>();

        for (Enum elem : enumList) {
            list.add(elem.ordinal());
        }

        return list;
    }

    protected static List<Integer> produceIdList(String joined) {
        String[] splitarray = joined.split(", ");
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < splitarray.length; i++)
            list.add(Integer.parseInt(splitarray[i]));

        return list;
    }

    public static String produceListString(List<Integer> list) {
        StringBuilder joined = new StringBuilder();

        for (int id : list) {
            joined.append(id);
            joined.append(", ");
        }

        return joined.toString();
    }

}
