<template>
  <div class="report-charts-title" >大仓大利数据中心</div>
  <div class="report-charts">
    <div class="charts" ref="chart1" ></div>
  </div>
</template>

<script setup >
import { nextTick, onMounted, reactive, ref, watch } from "vue";
import { tip, get, post, del, removeLocalToken, ddo } from "@/common";
import * as echarts from 'echarts';

const chart1 = ref();

let chartObj1;


const makeChart = (chartRef, option) => {
  const chartObj = echarts.init(chartRef.value);
  chartObj.setOption(option);
  return chartObj;
};

onMounted(() => {
  nextTick(() => {
    chartObj1 = makeChart(chart1, option1);
    chartObj1.resize({ height: 280 });
  });
});

// 刷新chart视图
const refreshChart = (chartObj, option) => {
  if (!chartObj) {
    nextTick(() => {
      refreshChart(chartObj, option);
    });
  } else {
    chartObj.setOption(option);
  }
};

// 1. 各仓库的库存情况
const option1 = reactive({
  title: {
    text: '在库库存信息'
  },
  legend: {
    orient: "horizontal",
    /* orient: 'vertical', */
    right: 5,
    /* top: 'center' */
  },
  tooltip: {
    
  },
  dataset: {
    dimensions: ['product', '西安仓库', '北京仓库', '上海仓库'],
    source: [
      { product: '', '西安仓库': 0, '北京仓库': 0, '上海仓库': 0 },
      { product: 'Milk Tea', 2015: 83.1, 2016: 73.4, 2017: 55.1 },
      { product: 'Cheese Cocoa', 2015: 86.4, 2016: 65.2, 2017: 82.5 },
      { product: 'Walnut Brownie', 2015: 72.4, 2016: 53.9, 2017: 39.1 } 
    ]
  },
  xAxis: {  type: 'category' },
  yAxis: {name: '单位：件/箱'},
  series: [{ type: 'bar' }, { type: 'bar' }, { type: 'bar' }]
});

// 监视器
watch(option1, (newOption, oldOption) => refreshChart(chartObj1, newOption));
// 获取仓库的商品库存
const getStoreInvent = () => {
  get('/statistics/store-invent').then(res => {
    const source = [{product: ''}];
    res.data.forEach(e => {
      source[0][e.storeName] = e.totalInvent;
    });
    option1.dataset.source = source;
  });
}
getStoreInvent();
</script>

<style scoped>
.report-charts-title{
  text-align: center;
  font-size: 30px;
  font-weight: bold;
  padding-bottom: 6px;
  letter-spacing: 8px;
  color:#100C2A;
}
.report-charts {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}

.report-charts .charts {
  width: 32%;
  height: 240px;
  flex: atuo;
  box-sizing: border-box;
    margin-left: 35%;
    margin-top: 10%;
}
.container{
  font-size: 17px;
  font-family: 黑体;
  display: flex;
  flex-direction: column;
}
.container>div{
  margin-top: 10px;
}
</style>