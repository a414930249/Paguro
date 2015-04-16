// Copyright (c) 2015-04-06 PlanBase Inc. & Glen Peterson
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
import org.organicdesign.fp.Mutable;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SequenceFlatMappedTest {
    @Test
    public void singleFlatMap() {
        assertEquals(Sequence.emptySequence(),
                     Sequence.ofArray(1, 2, 3, 4, 5, 6, 7, 8, 9).flatMap(null));

        assertEquals(Sequence.emptySequence(),
                     Sequence.emptySequence().flatMap(null));

        assertArrayEquals(new Integer[] {},
                          Sequence.ofArray(1, 2, 3, 4, 5, 6, 7, 8, 9)
                                  .flatMap(null).toTypedArray());

        assertArrayEquals(new Integer[] { 1,2,3, 2,4,6, 3,6,9, 4,8,12, 5,10,15, 6,12,18,
                                  7,14,21, 8,16,24, 9,18,27},
                          Sequence.ofArray(1, 2, 3, 4, 5, 6, 7, 8, 9)
                                  .flatMap(i -> Sequence.ofArray(i, i * 2, i * 3)).toTypedArray());

        assertArrayEquals(new String[]{"1", "2", "2", "3", "3", "4"},
                          Sequence.ofArray(1, 2, 3)
                                  .flatMap(i -> Sequence.ofArray(String.valueOf(i),
                                                                 String.valueOf(i + 1))).toTypedArray());

    }

    // TODO: Start Here!
    @Test public void flatEmpty() {
        assertEquals(Sequence.emptySequence(),
                     Sequence.ofArray(1, 2, 3, 4, 5, 6, 7, 8, 9).flatMap((a) -> Sequence.emptySequence()));

        assertEquals(Sequence.emptySequence(),
                     Sequence.ofArray().flatMap((a) -> Sequence.emptySequence()));

        // This tests that I didn't just look ahead 2 or 3 times.  That the look-ahead is sufficient.
        Mutable.ObjectRef<Integer> count = Mutable.ObjectRef.of(0);
        assertArrayEquals(new String[]{"a9", "b9"},
                          Sequence.ofArray(1, 2, 3, 4, 5, 6, 7, 8, 9)
                                  .flatMap((a) -> {
                                      count.set(count.value() + 1);
                                      return (count.value() > 8)
                                             ? Sequence.ofArray("a" + a, "b" + a)
                                             : Sequence.emptySequence();
                                  }).forEach((item) -> {
                              System.out.println("Item " + item);
                              return null;
                          })
                                  .toTypedArray());

        count.set(0);
        assertArrayEquals(new String[]{"c8", "d8", "c9", "d9"},
                          Sequence.ofArray(1, 2, 3, 4, 5, 6, 7, 8, 9)
                                  .flatMap((a) -> {
                                      count.set(count.value() + 1);
                                      return (count.value() > 7)
                                             ? Sequence.ofArray("c" + a, "d" + a)
                                             : Sequence.emptySequence();
                                  }).forEach((item) -> {
                              System.out.println("Item " + item);
                              return null;
                          })
                                  .toTypedArray());

        count.set(0);
        assertArrayEquals(new String[]{"e1", "f1", "e2", "f2"},
                          Sequence.ofArray(1, 2, 3, 4, 5, 6, 7, 8, 9)
                                  .flatMap((a) -> {
                                      count.set(count.value() + 1);
                                      return (count.value() < 3)
                                             ? Sequence.ofArray("e" + a, "f" + a)
                                             : Sequence.emptySequence();
                                  }).forEach((item) -> {
                              System.out.println("count: " + count.value() + " Item " + item);
                              return null;
                          })
                                  .toTypedArray());

        Mutable.ObjectRef<Sequence<Integer>> shrinkSeq = Mutable.ObjectRef.of(Sequence.ofArray(1, 2, 3));
        assertArrayEquals(new Integer[]{2, 3, 2},
                          Sequence.ofArray(1, 2, 3, 4, 5, 6, 7, 8, 9)
                                  .flatMap((a) -> {
                                      shrinkSeq.set(shrinkSeq.value().rest());
                                      return shrinkSeq.value();
                                  })
                                  .toTypedArray());

        // Now start by returning an emptySequence, then a seq of length 1, then length 2, etc.
        // The first emptySequence should not end the processing.
        Mutable.ObjectRef<Sequence<Integer>> growSeq = Mutable.ObjectRef.of(Sequence.emptySequence());
        Mutable.ObjectRef<Integer> incInt = Mutable.ObjectRef.of(0);
        assertArrayEquals(new Integer[]{1, 1,2, 1,2,3,},
                          Sequence.ofArray(1, 2, 3)
                                  .flatMap((a) -> {
                                      if (incInt.value() > 0) {
                                          growSeq.set(growSeq.value().append(Sequence.ofArray(incInt.value())));
                                      }
                                      incInt.set(incInt.value() + 1);
                                      return growSeq.value();
                                  })
                                  .toTypedArray());

    }

    @Test
    public void flatMapChain() {
        assertEquals(Sequence.emptySequence(),
                     Sequence.ofArray(1, 2, 3, 4, 5, 6, 7, 8, 9)
                             .flatMap(null).flatMap(null).flatMap(null));

        assertEquals(Sequence.emptySequence(),
                     Sequence.emptySequence().flatMap(null).flatMap(null).flatMap(null));

        assertArrayEquals(new Integer[] {},
                          Sequence.ofArray(1, 2, 3, 4, 5, 6, 7, 8, 9)
                                  .flatMap(null).flatMap(null).flatMap(null).toTypedArray());

        assertArrayEquals(new Integer[] { 1,2, 2,3, 3,4, 10,11, 20,21, 30,31},
                          Sequence.ofArray(1, 10)
                                  .flatMap(i -> Sequence.ofArray(i, i * 2, i * 3))
                                  .flatMap(i -> Sequence.ofArray(i, i + 1))
                                  .toTypedArray());
    }
}
