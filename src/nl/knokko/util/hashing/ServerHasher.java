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

import nl.knokko.util.hashing.result.HashResult;
import nl.knokko.util.random.PseudoRandom;
import nl.knokko.util.random.Random;
import nl.knokko.util.random.RandomArray;

import static nl.knokko.util.random.PseudoRandom.Configuration.LEGACY;;

public class ServerHasher extends Hasher {

	public static HashResult tempHash(int[] pass, int[] temp) {
		Random random = new RandomArray(
				new PseudoRandom(pass[0], temp[0], pass[1], temp[1], pass[2], pass[3], temp[2], pass[4], LEGACY),
				new PseudoRandom(pass[5], temp[3], pass[6], temp[4], pass[7], pass[8], temp[5], pass[9], LEGACY),
				new PseudoRandom(pass[10], temp[6], pass[11], temp[7], pass[12], pass[13], temp[8], pass[14], LEGACY));
		return new HashResult(random.nextInts(50));
	}
}