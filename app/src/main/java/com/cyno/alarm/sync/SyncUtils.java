/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyno.alarm.sync;

import com.cyno.alarm.database.AlarmContentProvider;
import com.cyno.alarmclock.R;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


/**
 * Static helper methods for working with the sync framework.
 */
public class SyncUtils {
	private static final String PREF_SETUP_COMPLETE = "setup_complete";
	private static final long SYNC_FREQUENCY = 10 * 60;

	/**
	 * Create an entry for this application in the system account list, if it isn't already there.
	 *
	 * @param context Context
	 */
	public static boolean setSyncAccount(Context context) {
		
		boolean newAccount = false;
		boolean setupComplete = PreferenceManager
				.getDefaultSharedPreferences(context).getBoolean(PREF_SETUP_COMPLETE, false);

		// Create account, if it's missing. (Either first run, or user has deleted account.)
		Account account = new Account(context.getString(R.string.app_name) , context.getString(R.string.app_package_name));
		AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
		if (accountManager.addAccountExplicitly(account, null, null)) {
			Log.d("sync", "creating new account");
			ContentResolver.setIsSyncable(account, AlarmContentProvider.AUTHORITY, 1);
			ContentResolver.setMasterSyncAutomatically(true);
			ContentResolver.setSyncAutomatically(account,AlarmContentProvider.AUTHORITY, true);
			ContentResolver.addPeriodicSync(account , context.getString(R.string.authority) , Bundle.EMPTY , SYNC_FREQUENCY);
			newAccount = true;
		}
		if (newAccount || !setupComplete) {
			PreferenceManager.getDefaultSharedPreferences(context).edit()
			.putBoolean(PREF_SETUP_COMPLETE, true).commit();
		}
		return newAccount;
	}


}
