/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.parsing.integrate.asserts.item;

import io.shardingsphere.core.parsing.integrate.asserts.SQLStatementAssertMessage;
import io.shardingsphere.core.parsing.integrate.jaxb.item.ExpectedDistinctSelectItem;
import io.shardingsphere.core.parsing.parser.context.selectitem.DistinctSelectItem;
import io.shardingsphere.core.parsing.parser.context.selectitem.SelectItem;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Distinct select item assert.
 *
 * @author panjuan
 */
@RequiredArgsConstructor
final class DistinctSelectItemAssert {
    
    private final SQLStatementAssertMessage assertMessage;
    
    void assertDistinctSelectItems(final Set<SelectItem> actual, final List<ExpectedDistinctSelectItem> expected) {
        List<DistinctSelectItem> distinctSelectItems = getDistinctSelectItems(actual);
        assertThat(assertMessage.getFullAssertMessage("distinct select items size error: "), distinctSelectItems.size(), is(expected.size()));
        int count = 0;
        for (DistinctSelectItem each : distinctSelectItems) {
            assertDistinctSelectItem(each, expected.get(count));
            count++;
        }
    }
    
    private void assertDistinctSelectItem(final DistinctSelectItem actual, final ExpectedDistinctSelectItem expected) {
        assertThat(assertMessage.getFullAssertMessage("Distinct select item alias assertion error: "), actual.getAlias().orNull(), is(expected.getAlias()));
        assertThat(assertMessage.getFullAssertMessage("Distinct select item distinct column name size assertion error: "),
                actual.getDistinctColumnNames().size(), is(expected.getDistinctColumnNames().size()));
        int count = 0;
        for (String each : actual.getDistinctColumnNames()) {
            assertThat(assertMessage.getFullAssertMessage("Distinct select item distinct column name assertion error: "), each, is(expected.getDistinctColumnNames().get(count)));
            count++;
        }
    }
    
    private List<DistinctSelectItem> getDistinctSelectItems(final Set<SelectItem> actual) {
        List<DistinctSelectItem> result = new ArrayList<>(actual.size());
        for (SelectItem each : actual) {
            if (each instanceof DistinctSelectItem) {
                result.add((DistinctSelectItem) each);
            }
        }
        return result;
    }
}
