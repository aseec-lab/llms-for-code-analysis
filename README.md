# llmsfor-code-analysis
## Introduction
This is the online appendix for our paper [Large Language Models for Code Analysis: Do LLMs Really Do Their Job?](https://arxiv.org/abs/2310.12357).
## Data
![General Structure](images/FigAnalysisDiagram.jpg)
Our dataset consists of:
- Non-Obfuscated Code
1. C: Selected code sample from POJ-104 dataset and classic C benchmarks (Linpack, etc.);
2. JavaScript: The Octane benchmark and some web apps;
3. Python: Selected code samples from Google CodeSearchNet dataset;
- Obfuscated Code
1. Obfuscated JavaScript code (obtained by applying different obfuscation tchniques to the JavaScript branch of our Non-Obfuscated Code dataset);
2. Winner code of Internet Obfuscated C Code Contest (IOCCC);
## Results
<!-- ![Overall histogram regarding LoC statistics of our non-obfuscated source code dataset](images/FigCodeHist.jpg) -->
Results of our analysis:
1. Responses of different models on different code sample
2. Statistics
<!-- ## Case Studies
![Android Case Study](images/FigAndroidDiagram.jpg)
![WannaCry Case Study](images/FigWannaCry.jpg) -->
## Citation
```
@article{fang2023large,
  title={Large language models for code analysis: Do llms really do their job?},
  author={Fang, Chongzhou and Miao, Ning and Srivastav, Shaurya and Liu, Jialin and Zhang, Ruoyu and Fang, Ruijie and Asmita, Asmita and Tsang, Ryan and Nazari, Najmeh and Wang, Han and others},
  journal={arXiv preprint arXiv:2310.12357},
  year={2023}
}
```
