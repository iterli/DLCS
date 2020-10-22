<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--区域数据-->
      <el-col :span="4" :xs="24">
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
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
          <el-form-item label="名称" prop="enName">
            <el-input
              v-model="queryParams.enName"
              placeholder="请输入门禁设备名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="设备IP" prop="enIp">
            <el-input
              v-model="queryParams.enIp"
              placeholder="请输入门禁设备IP"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="所属区域" prop="areaId">
            <treeselect v-model="form.areaId" :options="areaOptions" :disable-branch-nodes="true"
                        :show-count="true" placeholder="请选择归属区域" style = "width: 200px;height: 7px;"/>
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
              v-hasPermi="['equipment:entrance:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['equipment:entrance:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['equipment:entrance:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['equipment:entrance:export']"
            >导出</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="entranceList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="设备名称" align="center" prop="enName" />
          <el-table-column label="设备IP" align="center" prop="enIp" />
          <el-table-column label="所属区域" align="center" prop="area.areaName" />
<!--          <el-table-column label="设备所属服务器" align="center" prop="info.ipaddr" />-->
          <el-table-column label="设备版本" align="center" prop="version" />
          <el-table-column label="在线状态" align="center" prop="linestatus" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['equipment:entrance:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['equipment:entrance:remove']"
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

    <!-- 添加或修改门禁设备信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" v-dialog-drag width="400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="enName">
          <el-input v-model="form.enName" placeholder="请输入门禁设备名称" style = "width: 220px" />
        </el-form-item>
        <el-form-item label="设备IP" prop="enIp">
          <el-input v-model="form.enIp" placeholder="请输入门禁设备IP" style = "width: 220px"/>
        </el-form-item>
        <el-form-item label="所属区域" prop="areaId">
          <treeselect v-model="form.areaId" :options="areaOptions" :disable-branch-nodes="true" :show-count="true" placeholder="请选择归属区域" style = "width: 220px"/>
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
import { listEntrance, getEntrance, delEntrance, addEntrance, updateEntrance, exportEntrance } from "@/api/equipment/entrance";
import { treeselect } from "@/api/system/area";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Entrance",
  components: { Treeselect },
  data() {
    return {
      // 区域名称
      areaName: undefined,
      // 区域树选项
      areaOptions: undefined,
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
      // 门禁设备信息表格数据
      entranceList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        areaId: undefined,
        info_id : undefined,
        enName: undefined,
        enIp: undefined,
        version: undefined,
        linestatus: undefined,
        infoId: undefined,
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
  watch: {
    // 根据名称筛选部门树
    areaName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询门禁设备信息列表 */
    getList() {
      this.loading = true;
      listEntrance(this.queryParams).then(response => {
        this.entranceList = response.rows;
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        enId: undefined,
        areaId: undefined,
        info_id : undefined,
        enName: undefined,
        enIp: undefined,
        version: undefined,
        linestatus: "0",
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
      this.ids = selection.map(item => item.enId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      this.open = true;
      this.title = "添加门禁设备信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const enId = row.enId || this.ids
      getEntrance(enId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门禁设备信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.enId != undefined) {
            updateEntrance(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addEntrance(this.form).then(response => {
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
      const enIds = row.enId || this.ids;
      this.$confirm('是否确认删除门禁设备信息编号为"' + enIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delEntrance(enIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门禁设备信息数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportEntrance(queryParams);
      }).then(response => {
        this.download(response.msg);
      }).catch(function() {});
    }
  }
};
</script>
