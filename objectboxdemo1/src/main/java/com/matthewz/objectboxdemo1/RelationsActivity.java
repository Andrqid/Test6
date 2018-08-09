package com.matthewz.objectboxdemo1;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.matthewz.objectboxdemo1.databinding.ActivityRelationsBinding;
import com.matthewz.objectboxdemo1.entity.Customer;
import com.matthewz.objectboxdemo1.entity.Order;
import com.matthewz.objectboxdemo1.entity.Person;
import com.matthewz.objectboxdemo1.entity.Pet;

import java.util.List;
import java.util.Random;

import io.objectbox.Box;
import io.objectbox.android.ObjectBoxLiveData;

public class RelationsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RelationsActivity";

    private Box<Person> mPersonBox;
    private Box<Pet> mPetBox;
    private Box<Customer> mCustomerBox;

    private Random mRandom = new Random();
    private ActivityRelationsBinding mBinding;
    private ObservableField<String> mPeronName;
    private ObservableField<String> mPetName;
    private ObservableField<String> mUnbindId;

    private int dropTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPersonBox = BaseApplication.sApplication.mBoxStore.boxFor(Person.class);
        mPetBox = BaseApplication.sApplication.mBoxStore.boxFor(Pet.class);
        mCustomerBox = BaseApplication.sApplication.mBoxStore.boxFor(Customer.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_relations);
        mBinding.setLifecycleOwner(this);
        mBinding.setPersonName(mPeronName = new ObservableField<>(""));
        mBinding.setPetName(mPetName = new ObservableField<>(""));
        mBinding.setUnbindId(mUnbindId = new ObservableField<>(""));
        mBinding.setCustomerLiveData(new ObjectBoxLiveData<Customer>(mCustomerBox.query().build()));

        mBinding.btnAdd.setOnClickListener(this);
        mBinding.btnQuery.setOnClickListener(this);
        mBinding.btnUnbind.setOnClickListener(this);
        mBinding.btnQueryPet.setOnClickListener(this);
        mBinding.btnAddCustomerAndOrder.setOnClickListener(this);
        mBinding.btnQueryCustomer.setOnClickListener(this);
        mBinding.btnDropYourFirstOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if(TextUtils.isEmpty(mPeronName.get()) || TextUtils.isEmpty(mPetName.get())){
                    Toast.makeText(this, "请补全主人姓名和宠物名字", Toast.LENGTH_SHORT).show();
                } else {
                    Pet pet = new Pet(System.currentTimeMillis() % 2 == 0 ? "猫" : "狗", mPetName.get(), mRandom.nextInt(10));
                    Person person = new Person(mPeronName.get(), mRandom.nextInt(60), pet);
                    mPersonBox.put(person);
                    Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_query:
                List<Person> people = mPersonBox.query().build().find();
                if(people.isEmpty()) {
                    Toast.makeText(this, "person table is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    for (Person person : people) {
                        Log.e(TAG, person.toString());
                    }
                }
                break;
            case R.id.btn_unbind:
                if(TextUtils.isEmpty(mUnbindId.get())) {
                    Toast.makeText(this, "请输入要解绑的主人id", Toast.LENGTH_SHORT).show();
                }else {
                    long unbindId = Long.parseLong(mUnbindId.get());
                    Person person = mPersonBox.get(unbindId);
                    if(null == person) {
                        Toast.makeText(this, "查无此人", Toast.LENGTH_SHORT).show();
                    }else {
                        person.getPet().setTarget(null);
                        mPersonBox.put(person);
                        Toast.makeText(this, "解绑完毕", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_query_pet:
                List<Pet> pets = mPetBox.query().build().find();
                if(pets.isEmpty()) {
                    Toast.makeText(this, "没有一只宠物", Toast.LENGTH_SHORT).show();
                }else {
                    for(Pet pet : pets) {
                        Log.e(TAG, pet.toString());
                    }
                }
                break;
            case R.id.btn_add_customer_and_order:
                Order order = new Order("虾米");
                Order order1 = new Order("螃蟹");
                Order order2 = new Order("鲸鱼");
                Customer customer = new Customer("三桑_" + System.currentTimeMillis());
                customer.getOrderList().add(order);
                customer.getOrderList().add(order1);
                customer.getOrderList().add(order2);
                mCustomerBox.put(customer);
                Toast.makeText(this, "one 2 many 插入完毕", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query_customer:
                List<Customer> all = mCustomerBox.getAll();
                if(!all.isEmpty()) {
                    for (Customer customer1 : all) {
                        Log.e(TAG, customer1.toString());
                    }
                } else {
                    Toast.makeText(this, "customer table is empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_drop_your_first_order:
                String s = mBinding.etDropYourFirstOrderId.getText().toString();
                int customerId = Integer.parseInt(s);
                Customer customer1 = mCustomerBox.get(customerId);
                if(null == customer1) {
                    Toast.makeText(this, "没有这个顾客！", Toast.LENGTH_SHORT).show();
                } else {
                    if(customer1.getOrderList().isEmpty()) {
                        Toast.makeText(this, "这个顾客的订单已经为空", Toast.LENGTH_SHORT).show();
                    } else {
                        customer1.getOrderList().remove(0);
                        if(dropTime % 2 == 0) {
                            customer1.getOrderList().applyChangesToDb();
                        }else {
                            mCustomerBox.put(customer1);
                        }
                        dropTime++;
                    }
                }
                break;
        }
    }
}
