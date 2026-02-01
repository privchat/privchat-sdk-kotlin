# 底部导航栏图标使用指南

## 📋 图标要求

### 1. **图标格式**

**推荐格式：**
- ✅ **PNG**（推荐）- 支持透明背景，兼容性最好
- ✅ **WEBP** - 体积更小，但需要确保平台支持
- ❌ **SVG** - KuiklyUI 目前不支持 SVG 格式

**Demo 中的使用：**
- Demo 项目使用的是 PNG 格式（`tabbar_*.png`）
- 部分图标使用 WEBP 格式（如 `ic_home_comment.webp`）

### 2. **图标大小**

**推荐尺寸：**
- **设计稿尺寸**：60x60px 或 90x90px（@2x/@3x）
- **显示尺寸**：30x30px（代码中已设置）
- **建议提供**：@2x (60x60) 和 @3x (90x90) 两套资源

**当前代码设置：**
```kotlin
size(30f, 30f) // 显示尺寸为 30x30
```

### 3. **图标命名规范**

**未选中状态：**
- `tabbar_live.png` - 直播
- `tabbar_ranking.png` - 排行榜
- `tabbar_entertainment.png` - 娱乐
- `tabbar_profile.png` - 我的

**选中状态（高亮）：**
- `tabbar_live_highlighted.png` - 直播（选中）
- `tabbar_ranking_highlighted.png` - 排行榜（选中）
- `tabbar_entertainment_highlighted.png` - 娱乐（选中）
- `tabbar_profile_highlighted.png` - 我的（选中）

### 4. **图标放置位置**

**资源文件路径：**
```
app/shared/src/commonMain/assets/
├── tabbar_live.png
├── tabbar_live_highlighted.png
├── tabbar_ranking.png
├── tabbar_ranking_highlighted.png
├── tabbar_entertainment.png
├── tabbar_entertainment_highlighted.png
├── tabbar_profile.png
└── tabbar_profile_highlighted.png
```

### 5. **图标设计建议**

**颜色：**
- 未选中：灰色（`#8E8E93`）
- 选中：蓝色（`#1DA1F2`）
- 注意：代码中已使用 `tintColor` 来改变颜色，所以图标本身可以是单色（黑色或白色）

**样式：**
- 使用线性图标（outline style）
- 选中状态可以使用填充图标（filled style）
- 保持图标简洁，易于识别
- 确保在小尺寸下清晰可见

**参考 Demo：**
- Demo 中的图标位于：`KuiklyUI/demo/src/commonMain/assets/common/themes/default/`
- 可以参考 `tabbar_home.png`、`tabbar_profile.png` 等图标的设计风格

## 🔧 使用方式

### 代码中的使用

```kotlin
Image {
    attr {
        size(30f, 30f)
        if (i == ctx.selectedTabIndex) {
            // 选中状态
            src(ImageUri.commonAssets(ctx.pageIconsHighlight[i]))
            tintColor(Color(0xFF1DA1F2))
        } else {
            // 未选中状态
            src(ImageUri.commonAssets(ctx.pageIcons[i]))
            tintColor(Color(0xFF8E8E93))
        }
    }
}
```

### 图标加载方式

- `ImageUri.commonAssets("tabbar_live.png")` - 从 commonMain/assets 加载
- `ImageUri.pageAssets("icon.png")` - 从页面特定 assets 加载

## 📝 当前需要的图标

根据导航栏配置，需要以下 8 个图标文件：

1. ✅ **直播**
   - `tabbar_live.png` - 未选中
   - `tabbar_live_highlighted.png` - 选中

2. ✅ **排行榜**
   - `tabbar_ranking.png` - 未选中
   - `tabbar_ranking_highlighted.png` - 选中

3. ✅ **娱乐**
   - `tabbar_entertainment.png` - 未选中
   - `tabbar_entertainment_highlighted.png` - 选中

4. ✅ **我的**
   - `tabbar_profile.png` - 未选中
   - `tabbar_profile_highlighted.png` - 选中

## 💡 推荐方案

### 方案 1：使用 Demo 中的图标（临时方案）
- 可以临时使用 Demo 中的图标文件
- 路径：`KuiklyUI/demo/src/commonMain/assets/common/themes/default/`
- 复制到：`app/shared/src/commonMain/assets/`
- 需要重命名为上述命名规范

### 方案 2：使用图标库（推荐）
- 推荐使用 [Iconify](https://iconify.design/) 或 [Feather Icons](https://feathericons.com/)
- 导出为 PNG 格式（60x60 或 90x90）
- 确保提供选中和未选中两套图标

### 方案 3：自定义设计
- 使用设计工具（Figma、Sketch 等）设计图标
- 导出为 PNG 格式
- 提供 @2x 和 @3x 两套资源

## ⚠️ 注意事项

1. **不支持 SVG**：KuiklyUI 目前不支持 SVG 格式，必须使用 PNG 或 WEBP
2. **透明背景**：建议使用透明背景的 PNG，以便适配不同主题
3. **颜色处理**：代码中已使用 `tintColor`，所以图标本身可以是单色
4. **文件大小**：建议单个图标文件不超过 50KB
5. **命名规范**：严格按照命名规范，否则无法正确加载

## 🔍 检查清单

- [ ] 图标格式为 PNG 或 WEBP
- [ ] 图标尺寸为 60x60 或 90x90（设计稿）
- [ ] 图标命名符合规范
- [ ] 图标放置在 `app/shared/src/commonMain/assets/` 目录
- [ ] 提供了选中和未选中两套图标
- [ ] 图标背景透明
- [ ] 图标在小尺寸下清晰可见

