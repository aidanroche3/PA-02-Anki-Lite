package cs3500.pa02.fileutilities;

import cs3500.pa02.comparators.CompareByDate;
import cs3500.pa02.comparators.CompareByModified;
import cs3500.pa02.comparators.CompareByName;
import java.util.ArrayList;

/**
 * Class for sorting a list of files given an order flag
 */
public class FileListSorter {

  private final ArrayList<MarkDownFile> fileList;
  private final String orderFlag;

  /**
   * Instantiates a file list sorter with a list of files and an order flag
   *
   * @param fileList the list of files to be sorted
   * @param orderFlag the order flag of how to sort the files
   */
  public FileListSorter(ArrayList<MarkDownFile> fileList, String orderFlag) {
    this.fileList = fileList;
    this.orderFlag = orderFlag;
  }

  /**
   * Returns a sorted list of files based on the order flag
   *
   * @return the sorted list of files
   */
  public ArrayList<MarkDownFile> getSortedList() {

    switch (orderFlag) {
      case "filename" -> fileList.sort(new CompareByName());
      case "created" -> fileList.sort(new CompareByDate());
      case "modified" -> fileList.sort(new CompareByModified());
      default -> throw new IllegalStateException("Cannot sort list.");
    }
    return fileList;
  }

}
