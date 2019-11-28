/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.example.myapplication;

import android.app.Application;
import android.car.CarNotConnectedException;



import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

// import android.car.CarProjectionManager.ProjectionStatusListener;

/**
 * Implementation {@link ViewModel} for {@link ContextualFragment}.
 *
 * Returns the first non-null {@link ContextualInfo} from a set of delegates.
 */
public class ContextualViewModel extends AndroidViewModel {
    private final MediatorLiveData<ContextualInfo> mContextualInfo = new MediatorLiveData<>();

    //private final List<LiveData<ContextualInfo>> mInfoDelegates;

    public ContextualViewModel(Application application) throws CarNotConnectedException{
        super(application);
       // this(application, getCarProjectionManager(application));
    }


//    private static CarProjectionManager getCarProjectionManager(Context context) throws CarNotConnectedException {
//
//        return (CarProjectionManager)
//                Car.createCar(context, new ServiceConnection() {
//                    @Override
//                    public void onServiceConnected(ComponentName name, IBinder service) {
//
//                    }
//
//                    @Override
//                    public void onServiceDisconnected(ComponentName name) {
//
//                    }
//                }).getCarManager(Car.PROJECTION_SERVICE);
//    }

    public MediatorLiveData<ContextualInfo> getContextualInfo() {
        return mContextualInfo;
    }





   /* @VisibleForTesting
    ContextualViewModel(Application application, CarProjectionManager carProjectionManager) {


        super(application);


        mInfoDelegates =
                Collections.unmodifiableList(Arrays.asList(
                        // new ProjectionContextualInfoLiveData(application, carProjectionManager),
                        new WeatherContextualInfoLiveData(application)
                ));

        Observer<Object> observer = x -> updateLiveData();
        for (LiveData<ContextualInfo> delegate : mInfoDelegates) {
            mContextualInfo.addSource(delegate, observer);
        }
    }

    private void updateLiveData() {
        for (LiveData<ContextualInfo> delegate : mInfoDelegates) {
            ContextualInfo value = delegate.getValue();
            if (value != null) {
                mContextualInfo.setValue(value);
                return;
            }
        }

        mContextualInfo.setValue(null);
    }

*/



}
