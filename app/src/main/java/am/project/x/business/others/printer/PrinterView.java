/*
 * Copyright (C) 2018 AlexMofer
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
package am.project.x.business.others.printer;

import com.am.mvp.core.MVPView;

/**
 * View
 */
interface PrinterView extends MVPView {
    /**
     * 打印状态变化
     *
     * @param state 状态信息
     */
    void onPrinterStateChanged(String state);

    /**
     * 打印结果
     *
     * @param result 结果信息
     */
    void onPrinterResult(String result);
}
