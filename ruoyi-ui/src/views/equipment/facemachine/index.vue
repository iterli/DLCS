<template>
  <div class="app-container">
      <el-row :gutter="20">
        <!--区域数据-->
        <el-col :span="4" :xs="24" >
          <div class="head-container">
            <el-input
              v-model="areaName"
              placeholder="请输入部门名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </div>
          <div class="head-container">
            <el-tree
              :data="areaOptions"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
              default-expand-all
              @node-click="handleNodeClick"
            />
          </div>
        </el-col>

        <!--头部搜索框-->
        <el-col :span="20" :xs="24" >

          <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">

            <el-form-item label="名称" prop="faceName">
              <el-input
                v-model="queryParams.faceName"
                placeholder="请输入人脸设备名称"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="设备IP" prop="faceIp">
              <el-input
                v-model="queryParams.faceIp"
                placeholder="请输入人脸设备IP"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="在线状态" prop="linestatus">
              <el-select v-model="queryParams.linestatus" placeholder="请选择在线状态" clearable size="small">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </el-form-item>
            <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['equipment:facemachine:add']"
              >新增</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="success"
                icon="el-icon-edit"
                size="mini"
                :disabled="single"
                @click="handleUpdate"
                v-hasPermi="['equipment:facemachine:edit']"
              >修改</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['equipment:facemachine:remove']"
              >删除</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['equipment:facemachine:export']"
              >导出</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="primary"
                size="mini"
                @click="handleInforation"
                v-hasPermi="['equipment:facemachine:Inforation']"
                align="left"
              >获取设备基本信息</el-button>
            </el-col>
            <!--      <el-col :span="1.5">-->
            <!--        <el-button-->
            <!--          type="primary"-->
            <!--          size="mini"-->
            <!--          @click="handleRegisterInfo"-->
            <!--          v-hasPermi="['equipment:facemachine:info']"-->
            <!--        >查看注册信息</el-button>-->
            <!--      </el-col>-->
            <!--      <el-col :span="1.5">-->
            <!--        <el-button-->
            <!--          type="primary"-->
            <!--          size="mini"-->
            <!--          @click="handleRestart"-->
            <!--          v-hasPermi="['equipment:facemachine:restart']"-->
            <!--        >重启设备</el-button>-->
            <!--      </el-col>-->
            <!--      <el-col :span="1.5">-->
            <!--        <el-button-->
            <!--          type="primary"-->
            <!--          size="mini"-->
            <!--          @click="handleTiming"-->
            <!--          v-hasPermi="['equipment:facemachine:timing']"-->
            <!--        >设备校时</el-button>-->
            <!--      </el-col>-->
            <!--      <el-col :span="1.5">-->
            <!--        <el-button-->
            <!--          type="primary"-->
            <!--          size="mini"-->
            <!--          @click="handleReset"-->
            <!--          v-hasPermi="['equipment:facemachine:reset']"-->
            <!--        >重置设备</el-button>-->
            <!--      </el-col>-->
          </el-row>

          <el-table v-loading="loading" :data="facemachineList" @selection-change="handleSelectionChange" >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="名称" align="center" prop="faceName" />
            <el-table-column label="设备IP" align="center" prop="faceIp" />
            <el-table-column label="所属区域" align="center" prop="area.areaName"/>
            <el-table-column label="序列号" align="center" prop="serial" />
            <el-table-column label="心跳地址" align="center" prop="faceheartbeataddress" />
            <el-table-column label="回调地址" align="center" prop="callbackurl" />
            <el-table-column label="照片数" align="center" prop="facePicturecount" />
            <el-table-column label="特征数" align="center" prop="faceFeaturecount" />
            <el-table-column label="进出" align="center" prop="enterInto" :formatter="enterIntoFormat" />
            <el-table-column label="在线状态" align="center" prop="linestatus" />
            <el-table-column label="版本" align="center" prop="version" width="60px" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['equipment:facemachine:edit']"
                >修改</el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['equipment:facemachine:remove']"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-col>
      </el-row>

      <!-- 添加或修改人脸设备信息对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body v-dialog-drag>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="所属区域" prop="areaId">
            <treeselect v-model="form.areaId" :options="areaOptions" :disable-branch-nodes="true" :show-count="true" placeholder="请选择归属区域" style = "width: 250px"/>
          </el-form-item>
          <el-form-item label="设备名称" prop="faceName">
            <el-input v-model="form.faceName" placeholder="请输入人脸设备名称" style="width: 250px" />
          </el-form-item>
          <el-form-item label="设备IP" prop="faceIp">
            <el-input v-model="form.faceIp" placeholder="请输入人脸设备IP" style="width: 250px" />`
          </el-form-item>
          <el-form-item label="设备密码" prop="facePassword">
            <el-input v-model="form.facePassword" placeholder="请输入人脸设备密码" style="width: 250px">
              <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>
          <!--出入标识下拉框-->
          <el-form-item label="进出标识" prop="enterInto">
            <el-select v-model="form.enterInto" placeholder="出入标识" clearable size="small" style = "width : 120px">
              <el-option
                v-for="dict in enterIntoOptions"
                :key="dict.dictLabel"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              >{{dict.dictLabel}}</el-option>
            </el-select>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
import { listFacemachine, getFacemachine, delFacemachine, addFacemachine, updateFacemachine, exportFacemachine } from "@/api/equipment/facemachine";
import { treeselect } from "@/api/system/area";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";


export default {
  name: "Facemachine",
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 人脸设备信息表格数据
      facemachineList: [],
      //进出标志数据
      enterIntoOptions : [],
      // 区域名称
      areaName: undefined,
      // 区域树选项
      areaOptions: undefined,
      // 查询参数
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        areaId: undefined,
        faceName: undefined,
        faceIp: undefined,
        serial: undefined,
        facePassword: undefined,
        faceheartbeataddress: undefined,
        enterInto: undefined,
        callbackurl: undefined,
        facePicturecount: undefined,
        faceFeaturecount: undefined,
        linestatus: undefined,
        version: undefined,
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getDicts("sys_enter_into").then(response => {
      this.enterIntoOptions = response.data;
    });
  },
  watch: {
    // 根据名称筛选部门树
    areaName(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    /** 查询人脸设备信息列表 */
    getList() {
      this.loading = true;
      listFacemachine(this.queryParams).then(response => {
        this.facemachineList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.areaOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.areaId = data.id;
      this.getList();
    },

    // 进出标志字典翻译
    enterIntoFormat(row, column) {
      return this.selectDictLabel(this.enterIntoOptions, row.enterInto);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        faceId: undefined,
        areaId: undefined,
        faceName: undefined,
        faceIp: undefined,
        serial: undefined,
        facePassword: undefined,
        faceheartbeataddress: undefined,
        enterInto: undefined,
        callbackurl: undefined,
        facePicturecount: undefined,
        faceFeaturecount: undefined,
        linestatus: "0",
        version: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.faceId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      this.open = true;
      this.title = "添加人脸设备信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const faceId = row.faceId || this.ids
      getFacemachine(faceId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改人脸设备信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.faceId != undefined) {
            updateFacemachine(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addFacemachine(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const faceIds = row.faceId || this.ids;
      this.$confirm('是否确认删除人脸设备信息编号为"' + faceIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delFacemachine(faceIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有人脸设备信息数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportFacemachine(queryParams);
      }).then(response => {
        this.download(response.msg);
      }).catch(function() {});
    },


    /**
     * 获取设备基本信息
     * @param row
     */
    handleInforation(row){
      const faceIds = row.faceId || this.ids;
      this.$confirm("该操作将获取人脸设备信息", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return getFaceInforation(faceIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("获取成功");
      }).catch(function() {});
    }
  }
};
</script>
