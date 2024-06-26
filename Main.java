
import java.io.*;
import java.util.*;
import edu.augie.finalProgram.ahona.*;
import edu.augie.finalProgram.ahona.ArrayList;


public class Main {
    
    // This static method reads data from the specified file and returns the data as a two-dimensional array of strings.
    static String[][] getData(String fileName) throws IOException {
        // Create an empty list of string arrays to store the data.
        ArrayList<String[]> data = new ArrayList<>();

        // Open a buffered reader to read from the file.
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            // Read each line of the file until the end is reached.
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line on commas and trim each resulting string.
                String[] split = line.split(",");
                for (int i = 0; i < split.length; i++) {
                    split[i] = split[i].trim();
                }

                // Add the array of strings to the list of data.
                data.add(split);
            }
        }

        // Return the list of data as a two-dimensional array of strings.
        return data.toArray(new String[data.size()][data.get(0).length]);
    }


    static Map<String, String[][]> datas; //declare a static variable 'datas' of type Map with key-value pair as <String, String[][]>
    static String dataKey; //declare a static variable 'dataKey' of type String

    public static void main(String[] args) throws Exception {
        //initialize 'datas' with the data from the given files
        datas = new HashMap<String, String[][]>();
        datas.put("30_yr_stock_market_data", getData("C:\\Users\\royap\\OneDrive\\Desktop\\Project\\src\\30_yr_stock_market_data.csv"));
        datas.put("30_yr_stock_market_info", getData("C:\\Users\\royap\\OneDrive\\Desktop\\Project\\src\\30_yr_stock_market_info.csv"));

        //get the first element of the keySet of the 'datas' map and assign it to 'dataKey'
        dataKey = datas.keySet().iterator().next();

        //create an instance of the 'Main' class
        Main driver = new Main();


        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Hello! Welcome! Tell me what do you wanna see right now? For print treemodel press (t), for Data (d), If you wanna exit, press (e) ?");
                String choice = bufferedReader.readLine();

                switch (choice) {
                    case "t":
                        DataSet dataSet = new DataSet(dataKey, datas.get(dataKey));
                        TreeMap<String, Object> tree = new TreeMap<>();
                        tree.put(dataSet.getSplitOnFeature().getName(), null);
                        driver.processDataSet(dataSet, tree, "");
                        break;
                    case "d":
                        System.out.println("Choose the following dataset (30_yr_stock_market_data(D), 30_yr_stock_market_info(I)) ?");
                        String value = bufferedReader.readLine();
                        if ("D".equalsIgnoreCase(value)) {
                            dataKey = value;
                            String[][] stockData = getData("C:\\Users\\royap\\OneDrive\\Desktop\\Project\\src\\30_yr_stock_market_data.csv");
                            if (stockData != null) {
                                datas.put("30_yr_stock_market_data", stockData);
                                System.out.println(Arrays.deepToString(stockData));
                            } else {
                                System.out.println("Error reading stock market data. Please check the file path.");
                            }
                        } else if ("I".equalsIgnoreCase(value)) {
                            dataKey = value;
                            String[][] iData = getData("C:\\Users\\royap\\OneDrive\\Desktop\\Project\\src\\30_yr_stock_market_info.csv");
                            if (iData != null) {
                                datas.put("30_yr_stock_market_info", iData);
                                System.out.println(Arrays.deepToString(iData));
                            } else {
                                System.out.println("Error reading stock market info data. Please check the file path.");
                            }
                        } else {
                            System.out.println("Please enter a valid dataset name.");
                        }
                        break;
                    case "e":
                        return;
                }
            }
        }
    }

    void processDataSet(DataSet dataSet, TreeMap<String, Object> node, String featureValueName) {
        // Print the dataset
        if (dataSet.toString() != null) {
            System.out.println(dataSet);
        }

        // If the entropy is not 0, then the split on feature is printed
        if (dataSet.getEntropy() != 0) {
            System.out.println("Entropy: " + dataSet.getEntropy());
            System.out.println("Spilt at: " + dataSet.getSplitOnFeature());

            // Create a map of feature values to datasets
            HashMap<String, DataSet> featureDataSets = new HashMap<>();
            dataSet.getSplitOnFeature().getFeatureValues().forEach(featureValue ->
                    featureDataSets.put(featureValue.getName(),
                            dataSet.createDataSet(dataSet.getSplitOnFeature(), featureValue, dataSet.getData())));
            processDataSets(featureDataSets, node);
        }
        // If the entropy is 0, then the decision is printed
        else {
            String[][] data = dataSet.getData();
            String decision = "<" + data[0][data[0].length - 1] + " = " + data[1][data[0].length - 1] + ">";
            node.put(featureValueName, decision);
            System.out.println("Decision is " + decision);
        }
    }


    void processDataSets(HashMap<String, DataSet> featureDataSets, TreeMap<String, Object> parentNode) {
        // Iterate over each entry in the featureDataSets HashMap
        featureDataSets.forEach((key, value) -> {
            // Create a new TreeMap node
            TreeMap<String, Object> node = new TreeMap<>();

            // Assign the key as the node's name and the value as its child
            parentNode.put(key, node);

            // Continue processing the data using the processDataSet method
            processDataSet(value, node, key);
        });
    }

}