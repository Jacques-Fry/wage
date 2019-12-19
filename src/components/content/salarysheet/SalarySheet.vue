<template>
  <div class="salary-sheet">
    <table>
      <tr>
        <th>员工姓名</th>
        <th colspan="13">{{wage.wageTime}}</th>
        <th>操作</th>
      </tr>

      <tr>
        <td rowspan="2">{{wage.staffName}}</td>
        <td>基本工资</td>
        <td>上一月度等级</td>
        <td>岗位系数</td>
        <td>实习</td>
        <td>级别系数</td>
        <td>绩效</td>
        <td>应发工资</td>
        <td>日工资</td>
        <td>报销费用</td>
        <td>社保</td>
        <td>实发补贴</td>
        <td>个税</td>
        <td>PAID IN</td>
        <td rowspan="2">
          <el-button size="mini" type="primary" @click="$emit('look',wage.id)">详情</el-button>
          <el-button size="mini" type="warning" @click="updWage">编辑</el-button>
        </td>
      </tr>
      <tr>
        <td>{{wage.base | toFixed2}}</td>
        <td>{{wage.lastLevel}}</td>
        <td>{{wage.postCoefficient | toFixed2}}</td>
        <td>{{wage.internship }}</td>
        <td>{{wage.classificationCoefficient | toFixed2}}</td>
        <td>{{wage.performance | toFixed2}}</td>
        <td>{{wage.wagesPayable | toFixed2}}</td>
        <td>{{wage.dailyWages | toFixed2}}</td>
        <td>{{wage.claimExpense | toFixed2}}</td>
        <td>{{wage.socialSecurity | toFixed2}}</td>
        <td>{{wage.realSubsidy | toFixed2}}</td>
        <td>{{wage.incomeTax | toFixed2}}</td>
        <td>{{wage.paidIn | toFixed2}}</td>
      </tr>
    </table>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "SalarySheet",
  data() {
    return {
      centerDialogVisible: false
    };
  },
  props: {
    wage: {
      type: Object,
      default() {
        return {};
      }
    }
  },
  methods: {
    updWage() {
      this.$router.push("/updwage/" + this.wage.id);
    }
  },
  filters: {
    toFixed2(data) {
      return data.toFixed(2);
    }
  }
};
</script>

<style scoped>
.salary-sheet {
  padding: 20px 0;
}
table {
  position: relative;
  text-align: center;
  margin-left: 10px;
  border-collapse: collapse;

  width: calc(100% - 20px);
  font-size: 14px;
}
th,
td {
  border: 1px solid rgb(219, 219, 219);
  padding: 10px 10px;
}
</style>
