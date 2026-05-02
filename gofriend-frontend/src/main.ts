import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// 1. 引入你需要的组件
import {
    Button, NavBar, Icon, Tabbar, TabbarItem, Search, Divider, Col, Row,
    Tag, CellGroup, Cell, TreeSelect, Empty, Card, Skeleton, Form, Field,
    Tab, Tabs, Dialog, Popup, RadioGroup, Radio, Stepper, PickerGroup, DatePicker,
    TimePicker, Switch, FloatingBubble, PullRefresh
} from 'vant';
// 2. 引入组件样式
import 'vant/lib/index.css';

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.use(Button);
app.use(NavBar);
app.use(Icon);
app.use(Tabbar);
app.use(TabbarItem);
app.use(Search);
app.use(Divider);
app.use(Col);
app.use(Row);
app.use(Tag);
app.use(CellGroup);
app.use(Cell);
app.use(TreeSelect);
app.use(Empty);
app.use(Card);
app.use(Skeleton);
app.use(Form);
app.use(Field);
app.use(Tab);
app.use(Tabs);
app.use(Dialog);
app.use(Popup);
app.use(RadioGroup);
app.use(Radio);
app.use(Stepper);
app.use(PickerGroup);
app.use(DatePicker);
app.use(TimePicker);
app.use(Switch);
app.use(FloatingBubble);
app.use(PullRefresh);

app.mount('#app')



