/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package irds.usc.edu.timekeeper;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeRecord {
    private Date throwTime = null;


    TimeRecord(Date throwTime){
        this.throwTime = throwTime;
    }


    public Date getThrowTime() {
        return throwTime;
    }


    public String  getString(SimpleDateFormat sdf){

        return sdf.format(throwTime);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

        return  getString(sdf);
    }

    public String fileString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        return  getString(sdf);
    }

    public static void main(String[] args) {

        System.out.println(new TimeRecord(new Date()));
    }
}
