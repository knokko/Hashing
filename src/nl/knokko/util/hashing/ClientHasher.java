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

import nl.knokko.util.bits.ByteArrayBitOutput;
import nl.knokko.util.random.PseudoRandom;
import nl.knokko.util.random.Random;
import nl.knokko.util.random.RandomArray;

public class ClientHasher {

	public static ClientHashResult clientHash(String password, String salt) {
		ByteArrayBitOutput buffer = new ByteArrayBitOutput(2 * (password.length() + salt.length()));
		buffer.addChars(password.toCharArray());
		buffer.addChars(salt.toCharArray());
		Random random = RandomArray.createPseudo(PseudoRandom.Configuration.LEGACY, buffer.getRawBytes());
		
		int[] clientSessionSeed = random.nextInts(24);
		int[] serverSessionSeed = random.nextInts(24);
		byte[] clientStartSeed = random.nextBytes(256);
		int[] serverStartSeed = random.nextInts(24);
		byte[] testPayload = RandomArray.createPseudo(PseudoRandom.Configuration.LEGACY, clientStartSeed).nextBytes(300);
		
		return new ClientHashResult(clientStartSeed, serverStartSeed, clientSessionSeed, serverSessionSeed, testPayload);
	}
}