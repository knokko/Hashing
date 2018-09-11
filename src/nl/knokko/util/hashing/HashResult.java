/* 
 * The MIT License
 *
 * Copyright 2018 20182191.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.util.hashing;

import java.util.Arrays;

public class HashResult implements Comparable<HashResult> {
	
	private final int[] result;

	public HashResult(int...result) {
		this.result = result;
	}
	
	@Override
	public boolean equals(Object other){
		if(other instanceof HashResult){
			HashResult h = (HashResult) other;
			return Arrays.equals(result, h.result);
		}
		return false;
	}
	
	@Override
	public String toString(){
		return "HashResult(" + Arrays.toString(result) + ")";
	}
	
	@Override
	public int hashCode(){
		int hash = 0;
		for(int i : result)
			hash += i;
		return hash;
	}
	
	@Override
	public int compareTo(HashResult other){
		if(result.length > other.result.length)
			return 1;
		if(result.length < other.result.length)
			return -1;
		for(int index = 0; index < result.length; index++){
			if(result[index] > other.result[index])
				return 1;
			if(result[index] < other.result[index])
				return -1;
		}
		return 0;
	}
	
	public int[] get(){
		return result;
	}
}