/*
 * Copyright (C) 2008 The Android Open Source Project
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

package android.graphics.cts;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.test.AndroidTestCase;

import dalvik.annotation.BrokenTest;
import dalvik.annotation.TestTargets;
import dalvik.annotation.TestLevel;
import dalvik.annotation.TestTargetNew;
import dalvik.annotation.TestTargetClass;

@TestTargetClass(PathMeasure.class)
public class PathMeasureTest extends AndroidTestCase {
    private PathMeasure mPathMeasure;
    private Path mPath;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mPath = new Path();
        mPathMeasure = new PathMeasure();

    }

    @TestTargets({
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test constructor(s) of PathMeasure.",
            method = "PathMeasure",
            args = {}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "Test constructor(s) of PathMeasure.",
            method = "PathMeasure",
            args = {android.graphics.Path.class, boolean.class}
        )
    })
    public void testConstructor() {
        mPathMeasure = new PathMeasure();

        // new the PathMeasure instance
        Path path = new Path();
        mPathMeasure = new PathMeasure(path, true);

        // new the PathMeasure instance
        mPathMeasure = new PathMeasure(path, false);
    }

    @TestTargetNew(
        level = TestLevel.COMPLETE,
        notes = "Test getPosTan(float distance, float[] pos, float[] tan).",
        method = "getPosTan",
        args = {float.class, float[].class, float[].class}
    )
    public void testGetPosTan() {
        float distance = 1f;
        float[] pos = { 1f };
        float[] tan = { 1f };
        try {
            mPathMeasure.getPosTan(distance, pos, tan);
            fail("should throw exception");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        float[] pos2 = { 1f, 2f };
        float[] tan2 = { 1f, 3f };
        assertFalse(mPathMeasure.getPosTan(distance, pos2, tan2));

        mPathMeasure.setPath(mPath, true);
        mPath.addRect(1f, 2f, 3f, 4f, Path.Direction.CW);
        mPathMeasure.setPath(mPath, true);
        float[] pos3 = { 1f, 2f, 3f, 4f };
        float[] tan3 = { 1f, 2f, 3f, 4f };
        assertTrue(mPathMeasure.getPosTan(0f, pos3, tan3));
    }

    @TestTargetNew(
        level = TestLevel.COMPLETE,
        notes = "Test nextContour().",
        method = "nextContour",
        args = {}
    )
    public void testNextContour() {
        assertFalse(mPathMeasure.nextContour());
        mPath.addRect(1, 2, 3, 4, Path.Direction.CW);
        mPathMeasure.setPath(mPath, true);
        assertTrue(mPathMeasure.nextContour());
    }

    @TestTargetNew(
        level = TestLevel.COMPLETE,
        notes = "Test getLength().",
        method = "getLength",
        args = {}
    )
    public void testGetLength() {
        assertEquals(0f, mPathMeasure.getLength());
        mPath.addRect(1, 2, 3, 4, Path.Direction.CW);
        mPathMeasure.setPath(mPath, true);
        assertEquals(8.0f, mPathMeasure.getLength());
    }

    @TestTargetNew(
        level = TestLevel.COMPLETE,
        notes = "Test isClosed().",
        method = "isClosed",
        args = {}
    )
    @BrokenTest("Flaky test. new PathMeasure().isClosed() does not return consistent result")
    public void testIsClosed() {
        assertTrue(mPathMeasure.isClosed());
        mPathMeasure = null;
        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, false);
        assertFalse(mPathMeasure.isClosed());
    }

    @TestTargetNew(
        level = TestLevel.COMPLETE,
        notes = "Test setPath(Path path, boolean forceClosed).",
        method = "setPath",
        args = {android.graphics.Path.class, boolean.class}
    )
    public void testSetPath() {
        mPathMeasure.setPath(mPath, true);
        //There is no getter and we can't obtain any status about it.
    }

    @TestTargetNew(
        level = TestLevel.COMPLETE,
        notes = "Test getSegment(float startD, float stopD, Path dst,boolean startWithMoveTo).",
        method = "getSegment",
        args = {float.class, float.class, android.graphics.Path.class, boolean.class}
    )
    public void testGetSegment() {
        assertEquals(0f, mPathMeasure.getLength());
        mPath.addRect(1, 2, 3, 4, Path.Direction.CW);
        mPathMeasure.setPath(mPath, true);
        assertEquals(8f, mPathMeasure.getLength());
    }

    @TestTargetNew(
        level = TestLevel.COMPLETE,
        notes = "Test getMatrix(float distance, Matrix matrix, int flags).",
        method = "getMatrix",
        args = {float.class, android.graphics.Matrix.class, int.class}
    )
    public void testGetMatrix() {
        Matrix matrix = new Matrix();
        assertFalse(mPathMeasure.getMatrix(1f, matrix,
                PathMeasure.POSITION_MATRIX_FLAG));
        matrix.setScale(1f, 2f);
        mPath.addRect(1f, 2f, 3f, 4f, Path.Direction.CW);
        mPathMeasure.setPath(mPath, true);
        assertTrue(mPathMeasure.getMatrix(0f, matrix,
                PathMeasure.TANGENT_MATRIX_FLAG));
    }
}
