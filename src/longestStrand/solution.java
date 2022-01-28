package longestStrand;
import java.io.*;
import java.util.*;

public class solution {
    //read files
    public static byte[] getFileBytes(String file) {
        try {
            File f = new File(file);
            int length = (int) f.length();
            byte[] data = new byte[length];
            new FileInputStream(f).read(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //1-dimension DP algorithm to get longest common strand between two files
    public int LCS(byte[] a, byte[] b, int longest[], int[] offset, int[] temoffset){
        int temlongest = 0;
        int start1 = 0;
        int start2 = 0;
        //dp array
        int[][] common = new int[2][a.length];
        int cur = 1;
        int pre = 0;
        for(int i = 0; i < a.length; i++){
            if(a[i] == b[0]){
                common[pre][i] = 1;
            }else{
                common[pre][i] = 0;
            }
        }
        for(int i = 1; i < b.length; i++){
            for(int j = 0; j < a.length; j++){
                if(a[j] == b[i]) {
                    if(i == 0 || j == 0){
                        common[cur][j] = 1;
                    }else{
                        common[cur][j] = common[pre][j - 1] + 1;
                    }
                    //update length of longest strand and offset
                    if(common[cur][j] > longest[0]){
                        longest[0] = common[cur][j];
                        offset[0] = j - longest[0] + 1;
                        offset[1] = i - longest[0] + 1;
                    }
                    if(common[cur][j] > temlongest){
                        temlongest = common[cur][j];
                        temoffset[0] = j - temlongest + 1;
                        temoffset[1] = i - temlongest + 1;
                    }
                }
                else {
                    common[cur][j] = 0;
                }
            }
            cur = cur == 1 ? 0 : 1;
            pre = pre == 1 ? 0 : 1;
        }
        System.out.println("The two files' length of longest strand is " + temlongest);
        return temlongest;
    }

    public static void main(String[] args) {
        solution sol = new solution();
        //store all files

        Map<Integer, byte[]> map = new HashMap<Integer, byte[]>();
        //modify here to store different files
        map.put(1, getFileBytes("D:\\java_program\\Elu\\src\\longestStrand\\sample.1"));
        map.put(2, getFileBytes("D:\\java_program\\Elu\\src\\longestStrand\\sample.2"));
        map.put(3, getFileBytes("D:\\java_program\\Elu\\src\\longestStrand\\sample.3"));
        map.put(4, getFileBytes("D:\\java_program\\Elu\\src\\longestStrand\\sample.4"));
        map.put(5, getFileBytes("D:\\java_program\\Elu\\src\\longestStrand\\sample.5"));
        map.put(6, getFileBytes("D:\\java_program\\Elu\\src\\longestStrand\\sample.6"));
        map.put(7, getFileBytes("D:\\java_program\\Elu\\src\\longestStrand\\sample.7"));
        map.put(8, getFileBytes("D:\\java_program\\Elu\\src\\longestStrand\\sample.8"));
        map.put(9, getFileBytes("D:\\java_program\\Elu\\src\\longestStrand\\sample.9"));
        map.put(10, getFileBytes("D:\\java_program\\Elu\\src\\longestStrand\\sample.10"));
        //store name of files which has longest common strand and the offset
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        int[] file = new int[2];
        int[] offset = new int[2];
        int[] temoffset = new int[2];
        int[] longest = new int[]{0};
        //do DP function in each pair
        for(int i = 1; i <= map.size(); i++){
            for(int j = i + 1; j <= map.size(); j++){
                int tem = longest[0];
                sol.LCS(map.get(i), map.get(j), longest, offset, temoffset);
                if(longest[0] > tem){
                    file[0] = i;
                    file[1] = j;
                }
            }
        }
        //get the length of longest strand, files and offsets, store in result HashMap
        result.put(file[0], offset[0]);
        result.put(file[1], offset[1]);
        //longest strand
        byte[] ans = Arrays.copyOfRange(map.get(file[0]), offset[0], offset[0] + longest[0]);
        int q = longest[0];
        //check whether other files has this strand, if yes, add filename and offset into result Hashmap
        for(int i = 1; i <= map.size(); i++){
            if(i != file[0] || i != file[1]) {
                if (sol.LCS(ans, map.get(i), longest, offset, temoffset) == q) {
                    result.put(i, temoffset[1]);
                }
            }
        }
        System.out.println("Final result:");
        //output all filesname and offset
        for(Map.Entry<Integer, Integer> entry : result.entrySet()){
            System.out.println("file: sample." + entry.getKey() + " offset: " + entry.getValue());
        }
        //output length of longest strand
        System.out.println("length of longest strand " + longest[0]);
    }
}
