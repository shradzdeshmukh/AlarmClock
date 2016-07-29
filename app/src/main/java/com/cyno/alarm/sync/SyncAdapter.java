/*
 * Copyright 2013 The Android Open Source Project
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


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.cyno.alarm.UtilsAndConstants.Utils;
import com.cyno.alarm.alarm_logic.AlarmService;
import com.cyno.alarm.models.Weather;
import com.cyno.alarm.networking.GetWeatherNetworking;

/**
 * Define a sync adapter for the app.
 *
 * <p>This class is instantiated in {@link SyncService}, which also binds SyncAdapter to the system.
 * SyncAdapter should only be initialized in SyncService, never anywhere else.
 *
 * <p>The system calls onPerformSync() via an RPC call through the IBinder object supplied by
 * SyncService.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

	private Context context;

	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
//		Log.d("sync", "synced");
		this.context = context;
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {

		Log.d("sync", "syncing all matches");
		if(Utils.isWeatherPermited(context)) {
			GetWeatherNetworking netowking = new GetWeatherNetworking(context, false, null);
			netowking.makeRequest(Weather.class);
		}
		context.startService(new Intent(context , AlarmService.class));
	}


}
