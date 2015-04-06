// Copyright (c) 2015-04-05 PlanBase Inc. & Glen Peterson
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.organicdesign.fp.permanent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class SequenceConcatenatedTest {

    @Test
    public void prepend() {
        assertArrayEquals(new Integer[] { 5, 6, 7, 8, 9 },
                          Sequence.ofArray(5,6,7,8,9)
                                  .prepend(null).toArray());

        assertArrayEquals(new Integer[] { 5, 6, 7, 8, 9 },
                          Sequence.ofArray(5,6,7,8,9)
                                  .prepend(Sequence.emptySequence()).toArray());

        assertArrayEquals(new Integer[] { 4, 5, 6, 7, 8, 9 },
                          Sequence.ofArray(5,6,7,8,9)
                                  .prepend(Sequence.ofArray(4)).toArray());

        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                          Sequence.ofArray(5,6,7,8,9)
                                  .prepend(Sequence.ofArray(1,2,3,4)).toArray());
    }

    @Test
    public void append() {
        assertArrayEquals(new Integer[] { 1, 2, 3, 4 },
                          Sequence.ofArray(1,2,3,4)
                                  .append(null).toArray());

        assertArrayEquals(new Integer[] { 1, 2, 3, 4 },
                          Sequence.ofArray(1,2,3,4)
                                  .append(Sequence.emptySequence()).toArray());

        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 },
                          Sequence.ofArray(1,2,3,4)
                                  .append(Sequence.ofArray(5)).toArray());

        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                          Sequence.ofArray(1,2,3,4)
                                  .append(Sequence.ofArray(5, 6, 7, 8, 9)).toArray());
    }

    @Test
    public void chainedPrependAppend() {
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                          Sequence.ofArray(5)                     //         5
                                  .prepend(Sequence.ofArray(4))   //       4,5
                                  .append(Sequence.ofArray(6))    //       4,5,6
                                  .prepend(Sequence.ofArray(2,3)) //   2,3,4,5,6
                                  .append(Sequence.ofArray(7,8))  //   2,3,4,5,6,7,8
                                  .prepend(Sequence.ofArray(1))   // 1,2,3,4,5,6,7,8
                                  .append(Sequence.ofArray(9))    // 1,2,3,4,5,6,7,8,9
                                  .toArray());

        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                          Sequence.ofArray(5)
                                  .prepend(null).prepend(null).prepend(null).prepend(null)
                                  .prepend(Sequence.emptySequence()).prepend(Sequence.emptySequence())
                                  .prepend(null)
                                  .append(Sequence.emptySequence()).append(Sequence.emptySequence())
                                  .append(null).append(null).append(null).append(null).append(null)
                                  .prepend(Sequence.ofArray(4))
                                  .prepend(null).prepend(null).prepend(null).prepend(null)
                                  .prepend(Sequence.emptySequence()).prepend(Sequence.emptySequence())
                                  .prepend(null)
                                  .append(Sequence.emptySequence()).append(Sequence.emptySequence())
                                  .append(null).append(null).append(null).append(null).append(null)
                                  .append(Sequence.ofArray(6))
                                  .prepend(null).prepend(null).prepend(null).prepend(null)
                                  .prepend(Sequence.emptySequence()).prepend(Sequence.emptySequence())
                                  .prepend(null)
                                  .append(Sequence.emptySequence()).append(Sequence.emptySequence())
                                  .append(null).append(null).append(null).append(null).append(null)
                                  .prepend(Sequence.ofArray(2,3))
                                  .prepend(null).prepend(null).prepend(null).prepend(null)
                                  .prepend(Sequence.emptySequence()).prepend(Sequence.emptySequence())
                                  .prepend(null)
                                  .append(Sequence.emptySequence()).append(Sequence.emptySequence())
                                  .append(null).append(null).append(null).append(null).append(null)
                                  .append(Sequence.ofArray(7,8))
                                  .prepend(null).prepend(null).prepend(null).prepend(null)
                                  .prepend(Sequence.emptySequence()).prepend(Sequence.emptySequence())
                                  .prepend(null)
                                  .append(Sequence.emptySequence()).append(Sequence.emptySequence())
                                  .append(null).append(null).append(null).append(null).append(null)
                                  .prepend(Sequence.ofArray(1))
                                  .prepend(null).prepend(null).prepend(null).prepend(null)
                                  .prepend(Sequence.emptySequence()).prepend(Sequence.emptySequence())
                                  .prepend(null)
                                  .append(Sequence.emptySequence()).append(Sequence.emptySequence())
                                  .append(null).append(null).append(null).append(null).append(null)
                                  .append(Sequence.ofArray(9))
                                  .prepend(null).prepend(null).prepend(null).prepend(null)
                                  .prepend(Sequence.emptySequence()).prepend(Sequence.emptySequence())
                                  .prepend(null)
                                  .append(Sequence.emptySequence()).append(Sequence.emptySequence())
                                  .append(null).append(null).append(null).append(null).append(null)
                                  .toArray());
    }
}
