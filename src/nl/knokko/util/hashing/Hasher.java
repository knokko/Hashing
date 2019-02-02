/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package nl.knokko.util.hashing;

import nl.knokko.util.random.Random;
import nl.knokko.util.random.RandomArray;

import static nl.knokko.util.random.PseudoRandom.Configuration.LEGACY;

import nl.knokko.util.bits.BitHelper;

public class Hasher {

	public static Random createRandom(int[] halfSeed1, int[] halfSeed2) {
		if (halfSeed1.length != halfSeed2.length) {
			throw new IllegalArgumentException("length1 is " + halfSeed1.length + " and length2 is " + halfSeed2.length);
		}
		
		int length = halfSeed1.length;
		byte[] bytes = new byte[8 * length];
		
		for (int index = 0; index < length; index++) {
			int byteIndex = index * 8;
			bytes[byteIndex++] = BitHelper.int2(halfSeed2[index]);
			bytes[byteIndex++] = BitHelper.int1(halfSeed1[index]);
			bytes[byteIndex++] = BitHelper.int2(halfSeed1[index]);
			bytes[byteIndex++] = BitHelper.int3(halfSeed2[index]);
			bytes[byteIndex++] = BitHelper.int0(halfSeed1[index]);
			bytes[byteIndex++] = BitHelper.int1(halfSeed2[index]);
			bytes[byteIndex++] = BitHelper.int0(halfSeed2[index]);
			bytes[byteIndex] = BitHelper.int3(halfSeed1[index]);
		}
		
		return RandomArray.createPseudo(LEGACY, bytes);
	}
}