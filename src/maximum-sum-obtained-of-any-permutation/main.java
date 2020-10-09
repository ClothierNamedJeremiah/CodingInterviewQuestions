import java.util.Arrays;

class Solution {
  /**
   * Time complexity: O(n log n)
   * Space complexity: O(n)
   */
  public int maxSumRangeQuery(int[] nums, int[][] requests) {
    long MOD = 1000000000 + 7;
    int[] freq = new int[nums.length];
    for (int[] request: requests) {
      int l = request[0];
      int r = request[1];
      freq[l] += 1;
      if (r + 1 < nums.length) {
        freq[r+1] -= 1;
      }
    }
    
    for (int i = 1; i < nums.length; i++) {
      freq[i] += freq[i-1];
    }
    
    Arrays.sort(nums);
    Arrays.sort(freq);
    
    long sum = 0;
    for (int r = nums.length - 1; r >= 0; r--) {
      sum += (long)nums[r] * freq[r];
    }
    
    return (int)(sum % MOD);
  }
}