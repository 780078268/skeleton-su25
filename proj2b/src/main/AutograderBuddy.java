package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap; // 确保导入了 NGramMap

public class AutograderBuddy {
    /**
     * 返回一个功能完整的 HyponymsHandler。
     * 这个方法会被自动评分系统和本地的 JUnit 测试调用。
     */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        // 1. 创建 WordNet 对象，使用方法参数提供的文件路径
        WordNet wn = new WordNet(synsetFile, hyponymFile);

        // 2. 创建 NGramMap 对象，使用方法参数提供的文件路径
        NGramMap ngm = new NGramMap(wordFile, countFile);

        // 3. 创建并返回一个初始化好的 HyponymsHandler
        return new HyponymsHandler(wn,ngm);
    }
}
